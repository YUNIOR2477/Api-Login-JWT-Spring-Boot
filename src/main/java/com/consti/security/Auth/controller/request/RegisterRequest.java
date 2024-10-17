package com.consti.security.Auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = " El campo del apellido no debe de estar vacio")
    private String firstname;

    @NotBlank(message = " El campo del nombre no debe de estar vacio")
    private String lastname;

    @NotBlank(message = " El campo del correo no debe de estar vacio")
    @Email(message = " digite correctamente un correo valido")
    private String email;

    @NotBlank(message = " El campo de la contraseña no debe de estar vacio")
    @Size(min = 7,message = " la contraseña debe de contener al menos 7 caracteres")
    private String password;

    private LocalDate dob;

}
