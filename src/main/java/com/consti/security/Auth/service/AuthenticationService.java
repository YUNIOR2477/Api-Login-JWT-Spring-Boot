package com.consti.security.Auth.service;

import com.consti.security.Auth.controller.request.AuthenticationRequest;
import com.consti.security.Auth.controller.request.AuthenticationResponse;
import com.consti.security.Auth.controller.request.RegisterRequest;
import com.consti.security.Auth.config.JwtService;
import com.consti.security.Auth.entity.Role;
import com.consti.security.Auth.entity.UserEntity;
import com.consti.security.Auth.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder pwEncoder;
    private final UserEntityRepository repository;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {

        var user = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(pwEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .role(Role.USER)
                .build();


        Optional<UserEntity> optionalStudent = repository.findByEmail(request.getEmail());
        if (optionalStudent.isPresent()) {
            return new AuthenticationResponse();
        }


        repository.save(user);
        var jwtToken = jwtService.generateToken(null, user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .firstname(user.getFirstname())
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(null, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .firstname(user.getFirstname())
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
}
