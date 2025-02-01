package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.PaginatedResponseDTO;
import com.hackacode.clinica.dto.PatientDTO;
import com.hackacode.clinica.service.IPatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Tag(name = "Patients")
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService patientService;

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<PatientDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(patientService.findAll(pageable)));
    }

    @GetMapping("/{patientId}")
    @ResponseBody
    public ResponseEntity<PatientDTO> getById(@PathVariable("patientId") Long patientId) {
        return ResponseEntity.ok(patientService.findById(patientId));
    }


}
