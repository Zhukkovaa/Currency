package com.example.servicesnew.service;

import com.example.servicesnew.feign.GiphyFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GiphyService {
    @Value("${feign.giphy.key}")
    private String apiKey;
    private final GiphyFeignClient giphy;
    public Map<String, Object> getServiceRandomGifByTag(String downTag){
        return giphy.getRandomGifByTag(apiKey, downTag, "g");
    }

}
