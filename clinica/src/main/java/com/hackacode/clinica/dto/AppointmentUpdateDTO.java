package com.hackacode.clinica.dto;

import com.hackacode.clinica.model.AppointmentStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppointmentUpdateDTO {
    private AppointmentStatus status;
}
