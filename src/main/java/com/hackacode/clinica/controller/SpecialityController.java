package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.SpecialityRequestDTO;
import com.hackacode.clinica.dto.SpecialityResponseDTO;
import com.hackacode.clinica.service.ISpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialities")
@RequiredArgsConstructor
public class SpecialityController {

    private final ISpecialityService specialityService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<SpecialityResponseDTO> create(@RequestBody SpecialityRequestDTO specialityRequestDTO) {
        SpecialityResponseDTO specialityResponseDTO = specialityService.saveSpeciality(specialityRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialityResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<SpecialityResponseDTO>> getAll(){
        return ResponseEntity.ok(specialityService.findAll());
    }
}
