package com.consti.security.service;

import com.consti.security.Auth.entity.Role;
import com.consti.security.Auth.entity.UserEntity;
import com.consti.security.Auth.utils.UserMapper;
import com.consti.security.Auth.controller.dto.UserDTO;
import com.consti.security.Auth.repository.UserEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new IllegalStateException(
                    "El usuario con id: " + userId + " no existe en la DB");
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

    @Transactional
    public void updateUser(Integer userId, String lastname, String firstname, String email) {
        UserEntity user = userEntityRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException(
                        "El usuario con id: " + userId + " no existe en la DB"));

        if (firstname != null && !firstname.isEmpty() && !Objects.equals(user.getFirstname(), firstname)) {
            user.setFirstname(firstname);
        }

        if (lastname != null && !lastname.isEmpty() && !Objects.equals(user.getLastname(), lastname)) {
            user.setLastname(lastname);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(user.getEmail(), email)) {
            Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(email);
            if (userEntityOptional.isPresent()) {
                throw new IllegalStateException("Correo electronico tomado");
            }
            user.setEmail(email);
        }
        userEntityRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        boolean exists = userEntityRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException(
                    "El usuario con id: " + userId + " no existe en la DB");
        }

        userEntityRepository.deleteById(userId);
    }
}
