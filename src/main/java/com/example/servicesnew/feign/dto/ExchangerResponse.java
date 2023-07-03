package com.example.servicesnew.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangerResponse {
    private String disclaimer;
    private String license;
    private Timestamp timestamp;
    private String base;
    private Map<String, Double> rates;


}
