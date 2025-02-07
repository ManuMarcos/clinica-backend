package com.hackacode.clinica.dto;

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
    private Long serviceId;
    private Long patientId;
    private Long doctorId;
}
