package com.hackacode.clinica.repository;

import com.hackacode.clinica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
}
