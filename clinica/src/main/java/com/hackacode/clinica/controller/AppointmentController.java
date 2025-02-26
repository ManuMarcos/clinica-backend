package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.*;
import com.hackacode.clinica.dto.appointment.AppointmentRequestDTO;
import com.hackacode.clinica.dto.appointment.AppointmentResponseDTO;
import com.hackacode.clinica.dto.appointment.AppointmentUpdateDTO;
import com.hackacode.clinica.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<AppointmentResponseDTO>> getAllAppointments(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(appointmentService.findAll(pageable)));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteById(appointmentId);
        return ResponseEntity.ok("Appointment deleted successfully");
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> save(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        return new ResponseEntity<>(appointmentService.save(appointmentRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/availability")
    public ResponseEntity<List<DoctorAvailabilityDTO>> getAvailabilityDates(
            @RequestParam Long serviceId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return ResponseEntity.ok(appointmentService.getAvailabilityForService(serviceId, from, to));
    }

    @PatchMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> update(@PathVariable Long appointmentId, @RequestBody AppointmentUpdateDTO appointmentUpdateDTO) {
        return new ResponseEntity<>(appointmentService.update(appointmentId, appointmentUpdateDTO), HttpStatus.OK);
    }


}
