package com.example.servicesnew.config;

import com.example.servicesnew.feign.ExchangerFeignClient;
import com.example.servicesnew.feign.GiphyFeignClient;
import com.example.servicesnew.repository.CurrencyRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class TestConfig {

    @MockBean
    private GiphyFeignClient giphyFeignClient;

    @MockBean
    private ExchangerFeignClient exchangerFeignClient;

    @MockBean
    private CurrencyRepository currencyRepository;
}
