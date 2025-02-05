package com.hackacode.clinica.service;


import com.hackacode.clinica.dto.AdminDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAdminService {
    AdminDTO save(AdminDTO adminDTO);
    Page<AdminDTO> findAll(Pageable pageable);
    boolean existsAdmin();
}
