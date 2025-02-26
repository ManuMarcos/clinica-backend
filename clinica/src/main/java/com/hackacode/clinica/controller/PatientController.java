package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.PaginatedResponseDTO;
import com.hackacode.clinica.dto.patient.PatientRequestDTO;
import com.hackacode.clinica.dto.patient.PatientResponseDTO;
import com.hackacode.clinica.service.IPatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Patients")
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> save(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return new ResponseEntity<>(patientService.save(patientRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<PatientResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(patientService.findAll(pageable)));
    }

    @GetMapping("/{patientId}")
    @ResponseBody
    public ResponseEntity<PatientResponseDTO> getById(@PathVariable("patientId") Long patientId) {
        return ResponseEntity.ok(patientService.findById(patientId));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> delete(@PathVariable("patientId") Long patientId) {
        patientService.deleteById(patientId);
        return ResponseEntity.ok("Patient deleted successfully");
    }
}
