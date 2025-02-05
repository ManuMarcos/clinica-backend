package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.hackacode.clinica.model.AppointmentStatus;
import com.hackacode.clinica.model.Service;
import com.hackacode.clinica.util.Views;

import java.time.LocalDateTime;

public class AppointmentResponseDTO {
    private Long id;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    @JsonView(Views.AppointmentView.class)
    private DoctorDTO doctor;
    @JsonView(Views.AppointmentView.class)
    private PatientDTO patient;
    @JsonView(Views.AppointmentView.class)
    private Service service;

}
