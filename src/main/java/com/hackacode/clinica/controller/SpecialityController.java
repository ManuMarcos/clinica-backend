package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.SpecialityRequestDTO;
import com.hackacode.clinica.dto.SpecialityResponseDTO;
import com.hackacode.clinica.service.ISpecialityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Specialities")
@RequestMapping("/specialities")
public class SpecialityController {

    private final ISpecialityService specialityService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<SpecialityResponseDTO> create(@RequestBody SpecialityRequestDTO specialityRequestDTO) {
        SpecialityResponseDTO specialityResponseDTO = specialityService.save(specialityRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialityResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<SpecialityResponseDTO>> getAll(){
        return ResponseEntity.ok(specialityService.findAll());
    }

    @GetMapping("/{specialityId}")
    public ResponseEntity<SpecialityResponseDTO> findById(@PathVariable Long specialityId) {
        return ResponseEntity.ok(specialityService.findById(specialityId));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{specialityId}")
    public SpecialityResponseDTO update(@PathVariable Long specialityId, @RequestBody SpecialityRequestDTO specialityRequestDTO) {
        return specialityService.update(specialityId, specialityRequestDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{specialityId}")
    public ResponseEntity<String> delete(@PathVariable Long specialityId) {
        specialityService.delete(specialityId);
        return ResponseEntity.ok("Speciality deleted succesfully");
    }

}
