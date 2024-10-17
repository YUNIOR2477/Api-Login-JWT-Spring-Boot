package com.consti.security.Auth.controller;

import com.consti.security.Auth.controller.request.AuthenticationRequest;
import com.consti.security.Auth.controller.request.AuthenticationResponse;
import com.consti.security.Auth.controller.request.RegisterRequest;
import com.consti.security.Auth.service.AuthenticationService;
import com.consti.security.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
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
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse response = service.register(request);
        if(response.getToken().isEmpty()){
            throw new ResourceNotFoundException("Usuario","correo",request.getEmail(),"ya");
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
           @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
