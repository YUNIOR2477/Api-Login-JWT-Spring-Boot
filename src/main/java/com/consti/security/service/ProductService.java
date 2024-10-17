package com.consti.security.service;

import com.consti.security.controller.dto.ProductDTO;
import com.consti.security.entity.Product;
import com.consti.security.exceptions.ResourceNotFoundException;
import com.consti.security.repository.IProductRepository;
import com.consti.security.utils.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new ResourceNotFoundException("Producto","Id",id.toString(),"no");
        }
        return productRepository.findById(id).map(productMapper::mapDTO);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(productMapper::mapDTO).toList();
    }

    public void save(ProductDTO productDTO) {
       Optional<Product> productOptional = productRepository.findByNumberSerial(productDTO.getNumberSerial());
        if (productOptional.isPresent()) {
            throw new ResourceNotFoundException("Producto","numero serial",productDTO.getNumberSerial(),"ya");
        }
        Product product = productMapper.mapEntity(productDTO);

        productRepository.save(product);
    }

    public void update(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto","Id",id.toString(),"no"));
            productRepository.save(productMapper.mapEntity(productDTO));

    }

    public void delete(Integer id){
        boolean exists=productRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Producto","Id",id.toString(),"no");
        }
        productRepository.deleteById(id);
    }
}
