package com.hackacode.clinica.dto.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ServiceRequestDTO {

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    private String name;

    @NotNull(message = "The description cannot be null")
    @NotBlank(message = "The description cannot be empty")
    private String description;

    @NotNull(message = "The service_code cannot be null")
    @NotBlank(message = "The service_code cannot be empty")
    @JsonProperty("service_code")
    private String serviceCode;

    @NotNull(message = "The price cannot be null")
    private BigDecimal price;
}
