package com.consti.security.utils;

import com.consti.security.controller.dto.ProductDTO;
import com.consti.security.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductDTO mapDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .numberSerial(product.getNumberSerial())
                .name(product.getName())
                .type(product.getType())
                .description(product.getDescription())
                .colors(product.getColors())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
    public Product mapEntity(ProductDTO productDTO){
        return Product.builder()
                .id(productDTO.getId())
                .numberSerial(productDTO.getNumberSerial())
                .name(productDTO.getName())
                .type(productDTO.getType())
                .description(productDTO.getDescription())
                .colors(productDTO.getColors())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .build();
    }
}
