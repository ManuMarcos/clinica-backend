package com.hackacode.clinica.config;
import com.hackacode.clinica.dto.admin.AdminRequestDTO;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.service.AdminService;
import com.hackacode.clinica.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.createDefaultUser();
    }
}
