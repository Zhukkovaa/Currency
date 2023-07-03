package com.example.servicesnew.service;

import com.example.servicesnew.exception.ExternalDataErrorException;
import com.example.servicesnew.exception.NotFoundException;
import com.example.servicesnew.feign.dto.ExchangerResponse;
import com.example.servicesnew.repository.CurrencyRepository;
import com.example.servicesnew.utils.GifUrlUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
class ChangeCurrencyServiceTest {

    @MockBean
    private CurrencyRepository currencyRepository;
    @MockBean
    private ExchangerService exchangerService;
    @MockBean
    private GiphyService giphyService;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private static final String DOWN_URL = "https://giphy.com/gifs/car-hate-engine-l2Sq8EYhA66vdOfAI";

    private static final Map<String, Object> GIPHY_MAP_DOWN = new LinkedHashMap<>() {{
        put("data", new LinkedHashMap<>() {{
            put("type", "gif");
            put("id", "l2Sq8EYhA66vdOfAI");
            put("url", DOWN_URL);
        }});
    }};



    @Test
    public void testGetGifAsBytes_WhenRateNotIncreased() throws Exception, ExternalDataErrorException, NotFoundException {
        // Заглушки для методов и зависимостей
        String currencyCode = "USD";
        String downTag = "broke";
        String imageUrl = GifUrlUtils.getUrlGif(GIPHY_MAP_DOWN);
        byte[] gifBytes = IOUtils.toByteArray(new URL(imageUrl));

        ChangeCurrencyService changeCurrencyService = new ChangeCurrencyService(exchangerService, new CurrencyService(currencyRepository), giphyService);

        ExchangerResponse mockResponse = new ExchangerResponse();
        mockResponse.setRates(new HashMap<>());
        mockResponse.getRates().put("USD", 1.1);
        mockResponse.getRates().put("EUR", 0.9);

        Mockito.doReturn(mockResponse).when(exchangerService).getExchangeRatesLatest();

        ExchangerResponse mockResponse2 = new ExchangerResponse();
        mockResponse2.setRates(new HashMap<>());
        mockResponse2.getRates().put("USD", 1.2);
        mockResponse2.getRates().put("EUR", 0.8);

        Mockito.doReturn(mockResponse2).when(exchangerService).getExchangeRatesOnDate(Mockito.anyString());

        when(giphyService.getServiceRandomGifByTag(downTag)).thenReturn(GIPHY_MAP_DOWN);
        // Выполнение метода, который должен быть протестирован
        byte[] result = changeCurrencyService.getGifAsBytes(currencyCode);

        // Проверка результатов
        assertArrayEquals(gifBytes, result);
        verify(giphyService).getServiceRandomGifByTag(downTag);
    }


}