package com.hackacode.clinica.dto.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class DoctorRequestDTO {
    private UserRequestDTO user;
    private BigDecimal salary;
    @JsonProperty(value ="speciality_id")
    private Long specialityId;
}
