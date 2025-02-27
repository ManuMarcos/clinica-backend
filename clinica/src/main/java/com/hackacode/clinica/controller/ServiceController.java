package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.appointment.AppointmentRequestDTO;
import com.hackacode.clinica.dto.appointment.AppointmentResponseDTO;
import com.hackacode.clinica.dto.service.ServiceRequestDTO;
import com.hackacode.clinica.dto.service.ServiceResponseDTO;
import com.hackacode.clinica.mapper.IAppointmentMapper;
import com.hackacode.clinica.service.AppointmentService;
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
    private final IAppointmentMapper appointmentMapper;
    private final AppointmentService appointmentService;

    @GetMapping
    public List<ServiceResponseDTO> getAll(Pageable pageable) {
        return serviceService.findAll(pageable);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponseDTO> getById(@PathVariable Long serviceId) {
        return ResponseEntity.ok(serviceService.findById(serviceId));
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDTO> save(@RequestBody @Valid ServiceRequestDTO serviceRequestDTO) {
        var individualService = serviceService.save(serviceRequestDTO);
        return new ResponseEntity<>(individualService, HttpStatus.CREATED);
    }


    @DeleteMapping("/{serviceId}")
    public ResponseEntity<String> delete(@PathVariable Long serviceId) {
        serviceService.delete(serviceId);
        return ResponseEntity.ok("Service deleted successfully.");
    }

    
}
