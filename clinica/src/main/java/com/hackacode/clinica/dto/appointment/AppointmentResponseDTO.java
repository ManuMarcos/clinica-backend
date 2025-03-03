package com.hackacode.clinica.dto.appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackacode.clinica.dto.doctor.DoctorResponseDTO;
import com.hackacode.clinica.dto.patient.PatientResponseDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.model.AppointmentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentResponseDTO {
    private Long id;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    private AppointmentStatus status;

    @JsonProperty("doctor_name")
    private String doctorName;

    @JsonProperty("doctor_speciality")
    private String doctorSpeciality;

    @JsonProperty("patient_name")
    private String patientName;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("service_code")
    private String serviceCode;

    private String comments;
}
