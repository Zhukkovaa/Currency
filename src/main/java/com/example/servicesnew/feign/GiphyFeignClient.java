package com.example.servicesnew.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

//аннотация, которая указывает на то, что данный интерфейс является клиентом для работы с удаленным сервисом Giphy.
// Аргументы value и url указывают на имя клиента и URL адрес удаленного сервиса соответственно.

@FeignClient(value = "giphy",
        url = "${feign.giphy.url}",
        path = "v1")
public interface GiphyFeignClient {
    //аннотация, которая указывает на то, что данный метод должен обрабатывать HTTP GET запросы с указанным путем.
    // Параметры запроса api_key, tag и rating передаются в URL адресе, а их значения берутся из
    // конфигурационного файла приложения.
    @GetMapping("/gifs/random")
    Map<String, Object> getRandomGifByTag(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag,
            @RequestParam("rating") String rating
    );
    //метод, который возвращает случайную GIF-анимацию по заданному тегу.
    // Аргумент tag указывается как переменная пути в URL адресе и передается в метод как параметр.
    // Возвращаемое значение - объект типа Map, содержащий информацию о GIF-анимации (URL адрес, размеры и т.д.).
}