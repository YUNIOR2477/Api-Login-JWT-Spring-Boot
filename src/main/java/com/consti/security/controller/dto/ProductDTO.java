package com.consti.security.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Integer id;

    private String numberSerial;

    private String type;

    private String name;

    private String description;

    private String colors;

    private Double price;

    private Integer stock;
}
