package com.example.servicesnew.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Schema
@Table(name="tr_currency" )
public class Currency {
    @Id
    @Schema(description = "ID", example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Base currency for check", example = "USD")
    private String base;

    @Schema(description = "Rate currency for check", example = "RUB")
    private String rate;

    @Schema(description = "Currency value", example = "86.5")
    @Column(columnDefinition = "numeric")
    private Double exchangeRate;

    @Schema(description = "Date", example = "2023-06-28T16:20:05.740Z")
    private LocalDateTime exchangeDate;

}
