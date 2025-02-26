package com.hackacode.clinica.controller;


import com.hackacode.clinica.dto.PaginatedResponseDTO;
import com.hackacode.clinica.dto.ServiceToDoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorRequestDTO;
import com.hackacode.clinica.dto.doctor.DoctorResponseDTO;
import com.hackacode.clinica.dto.workingHour.WorkingHourRequestDTO;
import com.hackacode.clinica.service.IDoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Doctors")
@RequestMapping("/doctors")
public class DoctorController {

    private final IDoctorService doctorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> save(@Valid @RequestBody DoctorRequestDTO doctorDTO) {
        return new ResponseEntity<>(doctorService.save(doctorDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<DoctorResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(doctorService.findAll(pageable)));
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResponseDTO<DoctorResponseDTO>> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(name = "speciality_id", required = false) Long specialityId,
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ){
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(doctorService.search(name, specialityId, pageable)));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> getById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.findById(doctorId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a working hour to a doctor",
            description = "Adds a new working hour for a specific doctor.")
    @PostMapping("/{doctorId}/working-hours")
    public ResponseEntity<Object> addWorkingHour(@PathVariable Long doctorId, @RequestBody WorkingHourRequestDTO workingHourDTO) {
        doctorService.addWorkingHour(doctorId, workingHourDTO);
        return new ResponseEntity<>("Working hour added successfully.", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @Operation(summary = "Delete a working hour from a doctor",
            description = "Removes a specific working hour by its ID.")
    @DeleteMapping("/{doctorId}/working-hours/{workingHourId}")
    public ResponseEntity<String> deleteWorkingHour(@PathVariable Long doctorId, @PathVariable Long workingHourId) {
        doctorService.deleteWorkingHour(doctorId, workingHourId);
        return new ResponseEntity<>("Working hour deleted successfully.", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping("/{doctorId}/services")
    public ResponseEntity<String> addServiceToDoctor(@PathVariable Long doctorId, @Valid @RequestBody ServiceToDoctorRequestDTO serviceToDoctorRequestDTO){
        doctorService.addService(doctorId, serviceToDoctorRequestDTO);
        return new ResponseEntity<>("Service added successfully.", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @DeleteMapping("/{doctorId}/services/{serviceId}")
    public ResponseEntity<String> removeServiceFromDoctor(@PathVariable Long doctorId, @PathVariable Long serviceId) {
        doctorService.removeService(doctorId,serviceId);
        return new ResponseEntity<>("Service removed successfully.", HttpStatus.OK);
    }


}
