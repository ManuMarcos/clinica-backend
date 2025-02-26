package com.hackacode.clinica.service;

import com.hackacode.clinica.dto.user.UserRequestDTO;
import com.hackacode.clinica.dto.user.UserResponseDTO;
import com.hackacode.clinica.exception.BadRequestException;
import com.hackacode.clinica.exception.ResourceNotFoundException;
import com.hackacode.clinica.mapper.IUserMapper;
import com.hackacode.clinica.model.User;
import com.hackacode.clinica.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(UserRequestDTO userRequestDTO) {
        if(userRepository.existsByDni(userRequestDTO.getDni())){
            throw new BadRequestException("User with dni: " + userRequestDTO.getDni() + " already exists");
        }
        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new BadRequestException("User with email: " + userRequestDTO.getEmail() + " already exists");
        }
        var newUser = userMapper.toEntity(userRequestDTO);
        newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return newUser;
    }

    @Override
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        var users = userRepository.findAll(pageable);
        var usersDTO = users.stream().map(userMapper::toResponseDTO).toList();
        return new PageImpl<>(usersDTO, pageable, usersDTO.size());
    }

    @Override
    public UserResponseDTO findById(Long id) {
        var user = this.getById(id);
        return userMapper.toResponseDTO(user);
    }

    @Override
    public void validateUniqueConstraints(UserRequestDTO userRequestDTO) {
        if(userRepository.existsByDni(userRequestDTO.getDni())){
            throw new IllegalArgumentException("Dni is already in use");
        }
        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new IllegalArgumentException("Email is already in use");
        }
    }

    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User with id " + id + " does not exist")
        );
    }


}
