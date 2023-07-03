package com.example.servicesnew.contorller;
import com.example.servicesnew.service.CurrencyService;
import com.example.servicesnew.service.ChangeCurrencyService;
import com.summerproject.abstraction.api.CurrencyApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
//@Tag(name = "Base controller", description = "Controller for check currency exchanges")
public class BaseController implements CurrencyApi {

    private final ChangeCurrencyService service;
    private final CurrencyService currencyService;

    @Override
    public ResponseEntity getGif(
            //@Parameter(description = "Currency code", required = true, example = "RUB")
            @PathVariable String currencyCode
    ) {
        return ResponseEntity.ok(service.getGifAsBytes(currencyCode));
    }

}