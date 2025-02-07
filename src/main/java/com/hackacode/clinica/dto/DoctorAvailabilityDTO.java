package com.hackacode.clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class DoctorAvailabilityDTO {
    @JsonProperty("doctor_id")
    private Long doctorId;

    @JsonProperty("doctor_name")
    private String doctorName;

    @JsonProperty("available_slots")
    private List<AvailableSlotDTO> availableSlots;
}
