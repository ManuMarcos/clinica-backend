package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.admin.AdminDTO;
import com.hackacode.clinica.dto.user.UserDTO;
import com.hackacode.clinica.mapper.AdminMapper;
import com.hackacode.clinica.model.Role;
import com.hackacode.clinica.repository.IAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService implements IAdminService {

    private final IAdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminDTO save(AdminDTO adminDTO) {
        //TODO: To refactor
        userService.validateUniqueConstraints(UserDTO.builder()
                .email(adminDTO.getEmail())
                .dni(adminDTO.getDni())
                .build()
        );
        var admin = adminMapper.toEntity(adminDTO);
        admin.setRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        return adminMapper.toDTO(adminRepository.save(admin));
    }

    @Override
    public Page<AdminDTO> findAll(Pageable pageable) {
        var admins = adminRepository.findAll(pageable);
        List<AdminDTO> adminDTOS = admins.stream()
                .map(adminMapper::toDTO)
                .toList();
        return new PageImpl<>(adminDTOS, pageable, adminDTOS.size());
    }

    @Override
    public boolean existsAdmin() {
        return adminRepository.count() > 0;
    }
}
