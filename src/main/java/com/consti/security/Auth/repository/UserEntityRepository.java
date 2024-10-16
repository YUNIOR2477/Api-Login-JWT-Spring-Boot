package com.consti.security.Auth.repository;

import com.consti.security.Auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

     Optional<UserEntity> findByEmail(String email);
}
