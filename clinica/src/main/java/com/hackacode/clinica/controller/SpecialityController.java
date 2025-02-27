package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.page.PaginatedResponseDTO;
import com.hackacode.clinica.dto.speciality.SpecialityRequestDTO;
import com.hackacode.clinica.dto.speciality.SpecialityResponseDTO;
import com.hackacode.clinica.service.ISpecialityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Specialities")
@RequestMapping("/specialities")
public class SpecialityController {

    private final ISpecialityService specialityService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Create a new specialty.",
            description = "This endpoint allows you to create a new specialty. You must provide the name.")
    public ResponseEntity<SpecialityResponseDTO> create(@RequestBody @Valid SpecialityRequestDTO specialityRequestDTO) {
        SpecialityResponseDTO specialityResponseDTO = specialityService.save(specialityRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(specialityResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Retrieve a list of all specialties",
            description = "This endpoint retrieves all specialties available in the system. It returns a list of specialty names or details. " +
                    "No filters are required")
    public ResponseEntity<PaginatedResponseDTO<SpecialityResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(specialityService.findAll(pageable)));
    }

    @GetMapping("/{specialityId}")
    @Operation(summary = "Retrieve a specialty by its unique ID",
            description = "This endpoint retrieves the details of a specific specialty using its unique ID. ")
    public ResponseEntity<SpecialityResponseDTO> findById(@PathVariable Long specialityId) {
        return ResponseEntity.ok(specialityService.findById(specialityId));
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Update an existing specialty by ID",
            description = "This endpoint allows you to update the details of an existing specialty. ")
    @PutMapping("/{specialityId}")
    public SpecialityResponseDTO update(@PathVariable Long specialityId, @RequestBody SpecialityRequestDTO specialityRequestDTO) {
        return specialityService.update(specialityId, specialityRequestDTO);
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Delete a specialty by its unique ID.",
            description = "This endpoint allows you to delete a specialty from the system using its unique ID. " +
                    "Once deleted, the specialty will no longer be available in the system.")
    @DeleteMapping("/{specialityId}")
    public ResponseEntity<String> delete(@PathVariable Long specialityId) {
        specialityService.delete(specialityId);
        return ResponseEntity.ok("Speciality deleted successfully");
    }

}
