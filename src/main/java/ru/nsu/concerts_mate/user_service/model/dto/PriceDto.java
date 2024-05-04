package ru.nsu.concerts_mate.user_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceDto {
    private int price;

    private String currency;
}
