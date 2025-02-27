package com.hackacode.clinica.dto.appointment;

import com.hackacode.clinica.model.AppointmentStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppointmentUpdateDTO {
    private AppointmentStatus status;
}
