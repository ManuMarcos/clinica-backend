package com.hackacode.clinica.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class AppointmentRequestDTO {

    private LocalDateTime dateTime;
    private Long serviceId;
    private Long patientId;
}
