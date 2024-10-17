package com.consti.security.repository;

import com.consti.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT p FROM Product p WHERE p.numberSerial LIKE %?1%")
    Optional<Product> findByNumberSerial(String numberSerial);
}
