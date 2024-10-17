package com.consti.security.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Integer id;

    @NotBlank(message = "El campo del numero serial no debe de estar vacio")
    private String numberSerial;

    @NotBlank(message = "El campo del tipo de producto no debe de estar vacio")
    private String type;

    @NotBlank(message = "El campo del nombre no debe de estar vacio")
    private String name;

    @NotBlank(message = "El campo de la descripcion no debe de estar vacio")
    private String description;

    @NotBlank(message = "El campo de colores no debe de estar vacio")
    private String colors;

    @Positive
    private Double price;

    @Positive
    private Integer stock;
}
