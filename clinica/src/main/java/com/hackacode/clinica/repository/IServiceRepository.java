package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceRepository extends JpaRepository<Service, Long> {
    boolean existsByServiceCode(String serviceCode);
}
