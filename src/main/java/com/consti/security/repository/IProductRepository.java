package com.consti.security.repository;

import com.consti.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Integer> {

    boolean findByNumberSerial(String numberSerial);
}
