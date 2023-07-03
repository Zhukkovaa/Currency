package com.example.servicesnew.service;

import com.example.servicesnew.entity.Currency;
import com.example.servicesnew.repository.CurrencyRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CurrencyServiceTest {


    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCurrency() {
        // Создание тестовых данных
        String base = "USD";
        String rate = "RUB";
        Double value = 86.5;
        LocalDateTime data = LocalDateTime.parse("2023-06-28T16:20:05.740");

        // Создание заглушки для сохранения
        ArgumentCaptor<Currency> currencyCaptor = ArgumentCaptor.forClass(Currency.class);

        // Выполнение метода, который должен быть протестирован
        currencyService.save(base, rate, value, data);

        // Проверка, что метод save был вызван с правильным аргументом
        Mockito.verify(currencyRepository).save(currencyCaptor.capture());
        Currency savedCurrency = currencyCaptor.getValue();

        // Проверка, что правильные данные были установлены в сохраненном объекте Currency
        assertEquals(base, savedCurrency.getBase());
        assertEquals(rate, savedCurrency.getRate());
        assertEquals(value, savedCurrency.getExchangeRate());
        assertEquals(data, savedCurrency.getExchangeDate());
    }

}