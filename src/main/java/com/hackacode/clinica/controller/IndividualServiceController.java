package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.IndividualServiceDTO;
import com.hackacode.clinica.service.IIndividualServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/individual_services")
@RequiredArgsConstructor
public class IndividualServiceController {

    private final IIndividualServiceService individualServiceService;

    @GetMapping
    public List<IndividualServiceDTO> getAll(Pageable pageable) {
        return individualServiceService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<IndividualServiceDTO> save(@RequestBody @Valid IndividualServiceDTO individualServiceDTO) {
        var individualService = individualServiceService.save(individualServiceDTO);
        return new ResponseEntity<>(individualService, HttpStatus.CREATED);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<String> delete(@PathVariable Long serviceId) {
        individualServiceService.delete(serviceId);
        return ResponseEntity.ok("Individual Service deleted successfully.");
    }
}
