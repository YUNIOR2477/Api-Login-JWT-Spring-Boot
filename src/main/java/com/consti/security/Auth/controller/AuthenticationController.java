package com.consti.security.Auth.controller;

import com.consti.security.Auth.controller.request.AuthenticationRequest;
import com.consti.security.Auth.controller.request.AuthenticationResponse;
import com.consti.security.Auth.controller.request.RegisterRequest;
import com.consti.security.Auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse response = service.register(request);
        if(response.getToken().isEmpty()){
            throw new IllegalStateException("El correo: "+request.getEmail()+" ya existe en la DB ");
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
