package com.hackacode.clinica.controller;


import com.hackacode.clinica.dto.DoctorDTO;
import com.hackacode.clinica.dto.PaginatedResponseDTO;
import com.hackacode.clinica.dto.WorkingHourDTO;
import com.hackacode.clinica.service.IDoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;

@RestController
@RequiredArgsConstructor
@Tag(name = "Doctors")
@RequestMapping("/doctors")
public class DoctorController {

    private final IDoctorService doctorService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<DoctorDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(doctorService.findAll(pageable)));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDTO> getById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.findById(doctorId));
    }

    @Operation(summary = "Add a working hour to a doctor",
            description = "Adds a new working hour for a specific doctor.")
    @PostMapping("/{doctorId}/working-hours")
    public ResponseEntity<String> addWorkingHour(@PathVariable Long doctorId, @RequestBody WorkingHourDTO workingHourDTO) {
        doctorService.addWorkingHour(doctorId, workingHourDTO);
        return new ResponseEntity<>("Working hour added successfully.", HttpStatus.CREATED);
    }



}
