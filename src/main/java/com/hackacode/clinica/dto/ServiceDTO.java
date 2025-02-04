package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndividualServiceDTO {

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
    private Double price;

}
