package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.DoctorAvailabilityDTO;
import com.hackacode.clinica.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Appointments")
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/availability")
    public ResponseEntity<List<DoctorAvailabilityDTO>> getAvailabilityDates(
            @RequestParam Long serviceId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return ResponseEntity.ok(appointmentService.getAvailabilityForService(serviceId, from, to));
    }


}
