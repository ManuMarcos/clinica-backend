package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PackageDTO {

    private Long id;
    private String name;
    @JsonProperty("package_code")
    private String packageCode;
    
    @JsonProperty(value = "individual_services",access = JsonProperty.Access.READ_ONLY)
    private List<IndividualServiceDTO> individualServices;
}
