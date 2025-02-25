package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter @Setter
public class AppointmentRequestDTO {

    private LocalDate date;
    private LocalTime time;
    @JsonProperty("service_id")
    private Long serviceId;

    @JsonProperty("patient_id")
    private Long patientId;

    @JsonProperty("doctor_id")
    private Long doctorId;
}
