package com.example.servicesnew;

import com.example.servicesnew.contorller.BaseController;
import com.example.servicesnew.feign.ExchangerFeignClient;
import com.example.servicesnew.feign.GiphyFeignClient;
import com.example.servicesnew.feign.dto.ExchangerResponse;
import com.example.servicesnew.repository.CurrencyRepository;
import com.example.servicesnew.service.ExchangerService;
import com.example.servicesnew.service.GiphyService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootTest
public abstract class AbstractTest {
    @Autowired
    protected ExchangerService exchangerService;
    @Autowired
    protected GiphyService giphyService;
    @Autowired
    protected BaseController baseController;

    @MockBean
    protected GiphyFeignClient giphyFeignClient;

    @MockBean
    protected ExchangerFeignClient exchangerFeignClient;
    @MockBean
    protected CurrencyRepository currencyRepository;


    //protected static final String API_OK = "123";
   // protected static final String API_NOT_OK = "321";
    private Map<String, Object> giphyDataMapUp = new LinkedHashMap<>() {{
        put("type", "gif");
        put("id", "wdUYfojBtiosK9UMCY");
        put("url", "https://giphy.com/gifs/morning-tea-wednesday-h0MTqLyvgG0Ss");

    }};

    private Map<String, Object> giphyMapUp = new LinkedHashMap<>() {{
        put("data", giphyDataMapUp);
    }};

    private Map<String, Object> giphyDataMapDown = new LinkedHashMap<>() {{
        put("type", "gif");
        put("id", "l2Sq8EYhA66vdOfAI");
        put("url", "https://giphy.com/gifs/car-hate-engine-l2Sq8EYhA66vdOfAI");
    }};

    private Map<String, Object> giphyMapDown = new LinkedHashMap<>() {{
        put("data", giphyDataMapDown);
    }};

    @BeforeEach
    public void setUp() {
        // Создание мок-ответа от внешнего сервиса
        ExchangerResponse mockResponse = new ExchangerResponse();
        mockResponse.setRates(new HashMap<>());
        mockResponse.getRates().put("USD", 1.0);
        mockResponse.getRates().put("EUR", 0.8);
        // Настройка поведения мок-клиента для каждого теста
        Mockito.when(exchangerFeignClient.getLatest(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockResponse);

        Mockito.when(giphyFeignClient.getRandomGifByTag(Mockito.anyString(), Mockito.eq("rich"), Mockito.anyString())).thenReturn(giphyMapUp);
        Mockito.when(giphyFeignClient.getRandomGifByTag(Mockito.anyString(), Mockito.eq("broke"), Mockito.anyString())).thenReturn(giphyMapDown);
//        Mockito.when(giphyFeignClient.getRandomGifByTag(Mockito.eq(API_NOT_OK), Mockito.anyString(), Mockito.anyString())).thenThrow(NullPointerException.class);
    }
}
