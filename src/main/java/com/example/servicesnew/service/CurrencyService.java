package com.example.servicesnew.service;
import com.example.servicesnew.entity.Currency;
import com.example.servicesnew.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    public void save(String base, String rate, Double value, LocalDateTime data) {
        Currency currency = new Currency();
        currency.setBase(base);
        currency.setRate(rate);
        currency.setExchangeRate(value);
        currency.setExchangeDate(data);
        currencyRepository.save(currency);
    }

}
