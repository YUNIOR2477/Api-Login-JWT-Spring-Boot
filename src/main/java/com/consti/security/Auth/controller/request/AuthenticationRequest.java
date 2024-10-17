package com.consti.security.Auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @Email(message = "Formato de correo no valido, digite un nuevo correo")
    @NotBlank(message = "El campo del correo no debe de estar vacio")
    private String email;

    @NotBlank(message = "el campo de la contraseña no debe de estar vacio")
    @Size(min = 7,message = "la contraseña debe de contener minimo 7 caracteres")
    private String password;
}
