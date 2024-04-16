package ru.nsu.concerts_mate.users_service.model.dto;

import lombok.Data;

@Data
public class PriceDto {
    private int price;

    private String currency;
}
