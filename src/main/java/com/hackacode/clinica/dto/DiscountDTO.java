package com.hackacode.clinica.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class DiscountDTO {

    private String description;
    private BigDecimal amount;
}
