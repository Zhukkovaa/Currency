package com.example.servicesnew.service;

import com.example.servicesnew.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class GiphyServiceTest extends AbstractTest {

    @Test
    public void getServiceRandomGifByTagTestRich() {
        Map<String, Object> map = giphyService.getServiceRandomGifByTag("rich");
        String  gifUrl = ((Map<String, Object>)map.get("data")).get("url").toString();
        Assertions.assertNotNull(gifUrl);
        Assertions.assertEquals(gifUrl, "https://giphy.com/gifs/morning-tea-wednesday-h0MTqLyvgG0Ss");

//        giphyFeignClient.getRandomGifByTag(API_OK, "rich", "g");
//        giphyFeignClient.getRandomGifByTag(API_NOT_OK, "rich", "g");
    }
    @Test
    public void getServiceRandomGifByTagTestBroke() {
        Map<String, Object> map = giphyService.getServiceRandomGifByTag("broke");
        String  gifUrl = ((Map<String, Object>)map.get("data")).get("url").toString();
        Assertions.assertNotNull(gifUrl);
        Assertions.assertEquals(gifUrl, "https://giphy.com/gifs/car-hate-engine-l2Sq8EYhA66vdOfAI");

    }
}
