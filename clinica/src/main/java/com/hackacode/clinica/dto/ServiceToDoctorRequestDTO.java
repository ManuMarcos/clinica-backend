package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ServiceToDoctorRequestDTO {
    @JsonProperty("service_id")
    private Long serviceId;
}
