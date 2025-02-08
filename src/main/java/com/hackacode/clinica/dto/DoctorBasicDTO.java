package com.hackacode.clinica.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DoctorBasicDTO {
    private String name;
    private String surname;
    private String speciality;
}
