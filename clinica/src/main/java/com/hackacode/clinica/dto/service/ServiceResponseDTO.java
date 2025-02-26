package com.hackacode.clinica.dto.service;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ServiceResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String serviceCode;
    private BigDecimal price;
}
