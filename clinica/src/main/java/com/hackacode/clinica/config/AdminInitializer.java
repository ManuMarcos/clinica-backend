package com.hackacode.clinica.config;
import com.hackacode.clinica.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserServiceImpl userService;

    @Override
    public void run(String... args) throws Exception {
        userService.createDefaultUser();
    }
}
