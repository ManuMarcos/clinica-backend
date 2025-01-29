package com.hackacode.clinica.controller;

import com.hackacode.clinica.dto.UserResponseDTO;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.model.User;
import com.hackacode.clinica.service.IDoctorService;
import com.hackacode.clinica.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    /*@GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable Long userId){
        UserResponseDTO userResponseDTO = userService.findById(userId);

        Role role = userResponseDTO.getRole();
        if(role.equals(Role.DOCTOR)){
            return ResponseEntity.ok(doctorService.findById(userId));
        }
        else if(role.equals(Role.PATIENT)){
            return ResponseEntity.ok(null);
        }
        else{
            return ResponseEntity.ok(userResponseDTO);
        }
    }*/

}
