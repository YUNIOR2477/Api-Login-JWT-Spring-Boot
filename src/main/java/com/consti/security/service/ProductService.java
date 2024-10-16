package com.consti.security.service;

import com.consti.security.controller.dto.ProductDTO;
import com.consti.security.entity.Product;
import com.consti.security.repository.IProductRepository;
import com.consti.security.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(IProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Optional<ProductDTO> findById(Integer id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(
                    "El producto con el id: " + id + " no existe en la DB");
        }
        return productRepository.findById(id).map(productMapper::mapDTO);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(productMapper::mapDTO).toList();
    }

    public ResponseEntity<?> save(ProductDTO productDTO) {
        boolean exists = productRepository.findByNumberSerial(productDTO.getNumberSerial());
        if (!exists) {
            throw new IllegalStateException(
                    "El producto con el codigo: " + productDTO.getNumberSerial() + " ya existe en la DB");
        }
        Product product = productMapper.mapEntity(productDTO);

        return ResponseEntity.ok(productRepository.save(product));
    }

    public void update(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "El producto con  el id: " + id + " no existe en la DB"));
        if (productDTO.getNumberSerial().isEmpty() && productDTO.getType().isEmpty() && productDTO.getName().isEmpty() && productDTO.getDescription().isEmpty() && productDTO.getColors().isEmpty() && productDTO.getPrice().isNaN()) {
            throw new IllegalStateException("Debes llenar todos los campos correctamente!");
        } else {
            productRepository.save(productMapper.mapEntity(productDTO));
        }
    }

    public void delete(Integer id){
        boolean exists=productRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(
                    "El producto con id: " + id + " no existe en la DB");
        }
        productRepository.deleteById(id);
    }
}
