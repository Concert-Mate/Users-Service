package ru.nsu.concertsmate.backend.model.dto;

import lombok.Data;

@Data
public class PriceDto {
    private int price;

    private String currency;
}
