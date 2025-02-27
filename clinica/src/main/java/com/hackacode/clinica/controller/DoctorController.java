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
    @Operation(summary = "Create a new doctor.",
            description = " This endpoint allows you to create a new doctor. You must provide the doctor’s personal details " +
                    "The doctor will be added to the system upon successful creation.")
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> save(@Valid @RequestBody DoctorRequestDTO doctorDTO) {
        return new ResponseEntity<>(doctorService.save(doctorDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieve all doctors or filter by name, specialty id",
            description = "This endpoint retrieves a list of doctors. You can filter the results by " +
                    "providing query parameters such as the doctor’s name, specialty ID, or other available filters. " +
                    "If no filters are provided, all doctors will be returned.")
    public ResponseEntity<PaginatedResponseDTO<DoctorResponseDTO>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long specialtyId,
            Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(doctorService.findAll(name,specialtyId,pageable)));
    }

    @GetMapping("/{doctorId}")
    @Operation(summary = "Retrieve a doctor by their unique ID",
            description = "This endpoint retrieves the details of a specific doctor using their unique ID. The response will include the user’s personal information, " +
                    "specialty, working hours, and any other available details")
    public ResponseEntity<DoctorResponseDTO> getById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.findById(doctorId));
    }

    @DeleteMapping("/{doctorId}")
    @Operation(summary = "Delete a doctor by ID.",
            description = "This endpoint allows you to delete a doctor from the system using their unique ID. " +
                    "Once deleted, the doctor will no longer be available in the system.")
    public ResponseEntity<String> deleteById(@PathVariable Long doctorId) {
        doctorService.deleteById(doctorId);
        return ResponseEntity.ok("Doctor removed successfully.");
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
    @Operation(summary = "Add a service to a doctor",
            description = "Adds an existing service to a doctor")
    @PutMapping("/{doctorId}/services/{serviceId}")
    public ResponseEntity<String> addServiceToDoctor(@PathVariable Long doctorId, @PathVariable Long serviceId){
        doctorService.addService(doctorId, serviceId);
        return new ResponseEntity<>("Service added successfully.", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @Operation(summary = "Remove a service from a doctor",
            description = "Removes an specific service by its ID from a doctor")
    @DeleteMapping("/{doctorId}/services/{serviceId}")
    public ResponseEntity<String> removeServiceFromDoctor(@PathVariable Long doctorId, @PathVariable Long serviceId) {
        doctorService.removeService(doctorId,serviceId);
        return new ResponseEntity<>("Service removed successfully.", HttpStatus.OK);
    }


}
