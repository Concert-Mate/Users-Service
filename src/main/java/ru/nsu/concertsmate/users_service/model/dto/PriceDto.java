package ru.nsu.concertsmate.users_service.model.dto;

import lombok.Data;

@Data
public class PriceDto {
    private int price;

    private String currency;
}
