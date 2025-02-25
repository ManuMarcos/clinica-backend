package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

}
