package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonPropertyOrder({"id","name", "package_code","services"})
public class PackageDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    private String name;

    @JsonProperty("package_code")
    @NotNull(message = "The package_code cannot be null")
    @NotBlank(message = "The package_code cannot be empty")
    private String packageCode;

    @JsonProperty(value = "services",access = JsonProperty.Access.READ_ONLY)
    private List<IndividualServiceDTO> individualServices;

    @NotNull(message = "The services_ids cannot be null")
    @JsonProperty(value = "individual_services_ids",access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> individualServicesIds;
}
