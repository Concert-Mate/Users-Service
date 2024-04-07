package ru.nsu.concertsmate.backend.model.dto;

import lombok.Data;

@Data
public class PriceDTO {
    private int price;

    private String currency;
}
