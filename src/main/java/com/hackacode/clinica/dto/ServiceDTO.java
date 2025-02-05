package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.model.Service;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ServiceDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    private String name;

    @NotNull(message = "The description cannot be null")
    @NotBlank(message = "The description cannot be empty")
    private String description;

    @JsonProperty("service_code")
    @NotNull(message = "The service_code cannot be null")
    @NotBlank(message = "The service_code cannot be empty")
    private String serviceCode;

    @NotNull(message = "The price cannot be null")
    private BigDecimal price;


    public static ServiceDTO from(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .price(service.getPrice())
                .description(service.getDescription())
                .serviceCode(service.getServiceCode())
                .build();
    }

    public static Service toEntity(ServiceDTO dto) {
        return Service.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .serviceCode(dto.getServiceCode())
                .build();
    }

}
