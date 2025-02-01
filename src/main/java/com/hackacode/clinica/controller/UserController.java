package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.PaginatedResponseDTO;
import com.hackacode.clinica.dto.UserResponseDTO;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.model.User;
import com.hackacode.clinica.service.IDoctorService;
import com.hackacode.clinica.service.IUserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users")
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<UserResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(PaginatedResponseDTO.fromPage(userService.findAll(pageable)));
    }

}
