package com.hackacode.clinica.config;
import com.hackacode.clinica.dto.admin.AdminRequestDTO;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.model.Role;
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
            var adminDTO = new AdminRequestDTO(new UserRequestDTO("admin","defaultAdmin",
                    "defaultadmin@clinica.com", "admin", "41915027",LocalDate.now(),
                    Role.ADMIN), "superAdmin");
            adminService.save(adminDTO);
        }
    }
}
