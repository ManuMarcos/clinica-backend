package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.hackacode.clinica.model.Doctor;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.util.Views;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@SuperBuilder
public class DoctorDTO extends UserDTO {

    private BigDecimal salary;

    private String speciality;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<WorkingHourDTO> workingHours;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long specialityId;

}
