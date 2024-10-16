package com.consti.security.controller;

import com.consti.security.controller.dto.ProductDTO;
import com.consti.security.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/findAll")
    public List<ProductDTO> findAll(){
        return productService.findAll();
    }

    @GetMapping("/find/{id}")
    public Optional<ProductDTO> findById(@PathVariable Integer id){
        return productService.findById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO){
        return productService.save(productDTO);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Integer id,@RequestBody ProductDTO productDTO){
        productService.update(id,productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        productService.delete(id);
    }
}
