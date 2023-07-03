package com.example.servicesnew.service;
import com.example.servicesnew.exception.ExternalDataErrorException;
import com.example.servicesnew.exception.NotFoundException;
import com.example.servicesnew.feign.dto.ExchangerResponse;
import com.example.servicesnew.utils.GifUrlUtils;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeCurrencyService {
    public String imageUrl;
    @Value("${uptag}")
    private String upTag = "rich";
    @Value("${downtag}")
    private String downTag = "broke";
    @Value("${base}")
    private String baseCurrency = "USD";
    @Value("${nochange.image}")
    private String noChangeImage = "https://i.giphy.com/media/OJac5MRF6xJpqQAcR5/giphy.gif";
    private final ExchangerService serviceExchanger;
    private final CurrencyService currencyService;
    private final GiphyService serviceGiphy;

    @SneakyThrows
    public byte[] getGifAsBytes(String currencyCode) throws RuntimeException {
        //проверка наличия кода валюты
        if ((currencyCode == null) || (currencyCode.isEmpty() || (currencyCode.isBlank()))) {
            throw new ValidationException("Пустой код валюты!");
        }
        //приведение кода валюты к верхнему регистру
        currencyCode = currencyCode.toUpperCase();
        log.info("Базовая валюта, относительно которой указаны курсы обмена = {}", baseCurrency);
        //Вызов метода isRateIncreased объекта service для проверки изменения курса валюты
        Boolean rateIncreased = isRateIncreased(currencyCode);

        //проверка наличия изменения курса валюты
        //изменений нет
        if (rateIncreased == null) {
            //присвоение переменной imageUrl значения noChangeImage в случае отсутствия изменения курса валюты
            imageUrl = noChangeImage;
        } else if (rateIncreased) { //проверка изменения курса валюты, изменения есть
            try {
                //вызов метода getRandomGifByTag объекта giphy для получения URL-адреса случайной GIF-анимации по тегу upTag
                imageUrl = GifUrlUtils.getUrlGif(serviceGiphy.getServiceRandomGifByTag(upTag));

            } catch (Exception e) {
                log.warn(e.toString());
            }

        } else {
            try {
                //вызов метода getRandomGifByTag объекта giphy для получения URL-адреса случайной GIF-анимации по тегу downTag
                imageUrl = GifUrlUtils.getUrlGif(serviceGiphy.getServiceRandomGifByTag(downTag));

            } catch (Exception e) {
                log.warn(e.toString());
            }
        }

        byte[] gifBytes;

        try (InputStream in = new URL(imageUrl).openStream()) {
            gifBytes = IOUtils.toByteArray(in);
        }

        return gifBytes;
    }

    // Метод проверки увеличения курса заданной валюты
    protected Boolean isRateIncreased(String currencyCode) throws ExternalDataErrorException, NotFoundException {
        Boolean result;

        // Получение текущих курсов валют
        ExchangerResponse exchangeRatesLatest = serviceExchanger.getExchangeRatesLatest();
        // Для получения курсов валют за день до текущего
        //создание объекта типа LocalDate, который содержит дату, предшествующую текущей дате на один день.
        LocalDate date = LocalDate.now().minusDays(1);
        ExchangerResponse exchangeRatesYesterday;
        exchangeRatesYesterday = serviceExchanger.getExchangeRatesOnDate(date.toString());

        //проверка наличия полученной информации о курсах валют
        if (exchangeRatesLatest == null || exchangeRatesYesterday == null) {
            // генерирование исключения типа ExternalDataErrorException в случае отсутствия информации о курсах валют
            throw new ExternalDataErrorException("Отсутствие информации о курсе валюты!");
        }
        //получение последнего значения курса валюты по ее коду
        Double latestRate = exchangeRatesLatest.getRates().get(currencyCode);
        //получение значения курса валюты на предыдущий день по ее коду
        Double yesterdayRate = exchangeRatesYesterday.getRates().get(currencyCode);
        //проверка наличия полученных значений курсов валют
        if (latestRate == null || yesterdayRate == null) {
            //генерирование исключения типа NotFoundException в случае отсутствия значения курса валюты
            throw new NotFoundException("Курс валюты = " + currencyCode + " не найден!");
        }
        currencyService.save(baseCurrency, currencyCode, latestRate, LocalDateTime.now());
        if (latestRate > yesterdayRate) {
            log.info("Курс валюты {} вырос! Вчера={}; Сегодня={}", currencyCode, yesterdayRate, latestRate);
            return true;
        } else if (latestRate < yesterdayRate) {
            log.info("Курс валюты {} снизился! Вчера={}; Сегодня={}", currencyCode, yesterdayRate, latestRate);
            return false;
        } else {
            log.info("Курс валюты {} не изменился! Вчера={}; Сегодня={}", currencyCode, yesterdayRate, latestRate);
            return null;
        }

    }
}