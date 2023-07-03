package com.example.servicesnew.feign;
import com.example.servicesnew.feign.dto.ExchangerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "exchanger", url = "${feign.exchanger.url}")
public interface ExchangerFeignClient {
    @GetMapping("latest.json")
        //метод, который возвращает информацию о последних курсах валют.
        // Возвращаемое значение - объект типа ExchangerResponse, содержащий информацию о курсах валют.
    ExchangerResponse getLatest(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base
    );

    @GetMapping("historical/{date}.json")
        //метод, который возвращает информацию о курсах валют на заданную дату.
        // Аргумент date указывается как переменная пути в URL адресе и передается в метод как параметр.
    ExchangerResponse getHistorical(
            @PathVariable("date") String date,
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base
    );
}