package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.DoctorAvailabilityDTO;
import com.hackacode.clinica.dto.ServiceDTO;
import com.hackacode.clinica.service.IServiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Services")
@RequestMapping("/services")
public class ServiceController {

    private final IServiceService serviceService;

    @GetMapping
    public List<ServiceDTO> getAll(Pageable pageable) {
        return serviceService.findAll(pageable);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceDTO> getById(@PathVariable Long serviceId) {
        return ResponseEntity.ok(serviceService.findById(serviceId));
    }

    @PostMapping
    public ResponseEntity<ServiceDTO> save(@RequestBody @Valid ServiceDTO serviceDTO) {
        var individualService = serviceService.save(serviceDTO);
        return new ResponseEntity<>(individualService, HttpStatus.CREATED);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<String> delete(@PathVariable Long serviceId) {
        serviceService.delete(serviceId);
        return ResponseEntity.ok("Service deleted successfully.");
    }


}
