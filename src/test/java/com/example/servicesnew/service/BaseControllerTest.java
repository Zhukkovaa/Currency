package com.example.servicesnew.service;
import com.example.servicesnew.AbstractTest;
import com.example.servicesnew.feign.dto.ExchangerResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class BaseControllerTest extends AbstractTest {
    @Test
    public void testBaseControllerCurrencyUp()  {

        ExchangerResponse mockResponse = new ExchangerResponse();
        mockResponse.setRates(new HashMap<>());
        mockResponse.getRates().put("USD", 1.1);
        mockResponse.getRates().put("RUB", 86.6);

        // Настройка поведения мок-клиента
        when(exchangerFeignClient.getHistorical(anyString(), anyString(), anyString()))
                .thenReturn(mockResponse);

        ExchangerResponse mockResponse2 = new ExchangerResponse();
        mockResponse2.setRates(new HashMap<>());
        mockResponse2.getRates().put("RUB", 87.8);

        when(exchangerFeignClient.getLatest(anyString(), anyString()))
                .thenReturn(mockResponse2);

        ResponseEntity<?> response = baseController.getGif("RUB");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        byte[] gif = (byte[]) response.getBody();
        assert gif != null;
        Assertions.assertTrue(gif.length != 0);

    }
    @Test
    public void testBaseControllerCurrencyDown() {
        ExchangerResponse mockResponse = new ExchangerResponse();
        mockResponse.setRates(new HashMap<>());
        mockResponse.getRates().put("USD", 1.1);
        mockResponse.getRates().put("RUB", 87.6);

        // Настройка поведения мок-клиента
        when(exchangerFeignClient.getHistorical(anyString(), anyString(), anyString()))
                .thenReturn(mockResponse);

        ExchangerResponse mockResponse2 = new ExchangerResponse();
        mockResponse2.setRates(new HashMap<>());
        mockResponse2.getRates().put("RUB", 86.6);

        when(exchangerFeignClient.getLatest(anyString(), anyString()))
                .thenReturn(mockResponse2);


        ResponseEntity<?> response = baseController.getGif("RUB");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        byte[] gif = (byte[]) response.getBody();
        assert gif != null;
        Assertions.assertTrue(gif.length != 0);

    }

}

