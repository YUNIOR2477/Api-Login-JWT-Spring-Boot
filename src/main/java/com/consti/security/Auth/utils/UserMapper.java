package com.consti.security.Auth.utils;

import com.consti.security.Auth.controller.dto.UserDTO;
import com.consti.security.Auth.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDTO toDTO(UserEntity user) {
        List<String> roles = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return UserDTO.builder().firstname(user.getFirstname())
                .lastname(user.getLastname())
                .dob(user.getDob())
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .roles(roles)
                .build();
    }

    public UserEntity toEntity(UserDTO dto) {

        return UserEntity.builder().firstname(dto.getFirstname())
                            .lastname(dto.getLastname())
                            .dob(dto.getDob()).id(dto.getId())
                            .role(dto.getRole()).build();
    }
}
