package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialityRepository extends JpaRepository<Speciality, Long> {
    boolean existsByName(String name);
}
