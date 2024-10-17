package com.consti.security.service;

import com.consti.security.Auth.controller.request.RegisterRequest;
import com.consti.security.Auth.entity.Role;
import com.consti.security.Auth.entity.UserEntity;
import com.consti.security.Auth.utils.UserMapper;
import com.consti.security.Auth.controller.dto.UserDTO;
import com.consti.security.Auth.repository.UserEntityRepository;
import com.consti.security.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsersService {
    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;

    @Autowired
    public UsersService(UserEntityRepository userEntityRepository, UserMapper userMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getUsers() {
        return userEntityRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    @Transactional
    public void changeUserRole(Integer userId) {
        boolean exists = userEntityRepository.existsById(userId);
        if (!exists) {
            throw new ResourceNotFoundException("Usuario", "Id", userId.toString(), "no");
        }
        if (userEntityRepository.getReferenceById(userId).getRole().equals(Role.USER)) {
            UserEntity user = userEntityRepository.getReferenceById(userId);

            user.setRole(Role.ADMIN);
        } else {
            throw new IllegalArgumentException(
                    "El rol del usuario: " + userId + " ya es administrador"
            );
        }
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public void updateUser(Integer userId, RegisterRequest update) {
        UserEntity user = userEntityRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Usuario", "Id", userId.toString(), "no"));

        user.setFirstname(update.getFirstname());
        user.setLastname(update.getLastname());
        user.setEmail(update.getEmail());
        user.setDob(update.getDob());
        user.setPassword(passwordEncoder().encode(update.getPassword()));

        userEntityRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        boolean exists = userEntityRepository.existsById(userId);
        if (!exists) {
            throw new ResourceNotFoundException("Usuario", "Id", userId.toString(), "no");
        }

        userEntityRepository.deleteById(userId);
    }
}
