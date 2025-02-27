package com.hackacode.clinica.service;


import com.hackacode.clinica.dto.admin.AdminRequestDTO;
import com.hackacode.clinica.dto.admin.AdminResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAdminService {
    AdminResponseDTO save(AdminRequestDTO adminRequestDTO);
    Page<AdminResponseDTO> findAll(Pageable pageable);
    boolean existsAdmin();
}
