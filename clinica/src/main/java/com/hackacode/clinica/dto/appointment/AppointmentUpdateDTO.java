package com.hackacode.clinica.dto.appointment;

import com.hackacode.clinica.model.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppointmentUpdateDTO {

    @NotNull
    private AppointmentStatus status;

    private String comments;
}
