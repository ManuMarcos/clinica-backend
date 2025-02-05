package com.hackacode.clinica.config;
import com.hackacode.clinica.dto.AdminDTO;
import com.hackacode.clinica.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        if(!adminService.existsAdmin()){
            var adminDTO = AdminDTO.builder()
                    .name("defaultAdmin")
                    .surname("defaultAdmin")
                    .dni("00000000")
                    .email("defaultadmin@clinica.com")
                    .password("admin")
                    .birthDate(LocalDate.now())
                    .position("defaultAdmin")
                    .build();
            adminService.save(adminDTO);
        }
    }
}
