package com.example.servicesnew.service;

import com.example.servicesnew.AbstractTest;
import com.example.servicesnew.feign.dto.ExchangerResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class ExchangerServiceTest extends AbstractTest {

    @Test
    void getExchangeRatesLatest() {
        // Вызов метода, который будет тестироваться
        ExchangerResponse result = exchangerService.getExchangeRatesLatest();
        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.getRates().size());
        assertEquals(1.0, result.getRates().get("USD"));
        assertEquals(0.8, result.getRates().get("EUR"));
    }

    @Test
    void getExchangeRatesOnDate() {
        // Создание мок-ответа от внешнего сервиса
        ExchangerResponse mockResponse = new ExchangerResponse();
        mockResponse.setRates(new HashMap<>());
        mockResponse.getRates().put("USD", 1.1);
        mockResponse.getRates().put("EUR", 0.9);

        // Настройка поведения мок-клиента
        Mockito.when(exchangerFeignClient.getHistorical(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockResponse);
        // Вызов метода, который будет тестироваться
        ExchangerResponse result = exchangerService.getExchangeRatesOnDate("2023-06-29");
        // Проверка результата
        assertNotNull(result);
        assertEquals(2, result.getRates().size());
        assertEquals(1.1, result.getRates().get("USD"));
        assertEquals(0.9, result.getRates().get("EUR"));
    }
}