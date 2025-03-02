package com.hackacode.clinica.controller;


import com.hackacode.clinica.dto.page.PaginatedResponseDTO;
import com.hackacode.clinica.dto.admin.AdminRequestDTO;
import com.hackacode.clinica.dto.admin.AdminResponseDTO;
import com.hackacode.clinica.service.impl.AdminServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Admins")
@RequestMapping("/admins")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminServiceImpl adminService;

    @PostMapping
    public ResponseEntity<AdminResponseDTO> create(@Valid @RequestBody AdminRequestDTO adminRequestDTO) {
        return new ResponseEntity<>(adminService.save(adminRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<AdminResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(adminService.findAll(pageable)));
    }

}
