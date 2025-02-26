package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.admin.AdminRequestDTO;
import com.hackacode.clinica.dto.admin.AdminResponseDTO;
import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.mapper.IAdminMapper;
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
    private final IAdminMapper adminMapper;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponseDTO save(AdminRequestDTO adminRequestDTO) {
        var savedUser = userService.save(adminRequestDTO.getUser());
        var admin = adminMapper.toEntity(adminRequestDTO);
        admin.setUser(savedUser);
        return adminMapper.toResponseDTO(adminRepository.save(admin));
    }

    @Override
    public Page<AdminResponseDTO> findAll(Pageable pageable) {
        var admins = adminRepository.findAll(pageable);
        var adminDTOS = admins.stream().map(adminMapper::toResponseDTO).toList();
        return new PageImpl<>(adminDTOS, pageable, adminDTOS.size());
    }

    @Override
    public boolean existsAdmin() {
        return adminRepository.count() > 0;
    }
}
