package com.example.servicesnew.service;

import com.example.servicesnew.feign.ExchangerFeignClient;
import com.example.servicesnew.feign.dto.ExchangerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangerService {
    @Value("${feign.exchanger.id}")
    private String appId;
    @Value("${base}")
    private String base;
    private final ExchangerFeignClient exchanger;
    public ExchangerResponse getExchangeRatesLatest(){
        return exchanger.getLatest(appId, base);
    }
    public ExchangerResponse getExchangeRatesOnDate(String date){
        return exchanger.getHistorical(date, appId, base);
    }
}
