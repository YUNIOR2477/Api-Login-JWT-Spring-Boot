package com.consti.security.Auth.config;

import com.consti.security.Auth.entity.UserEntity;
import com.consti.security.Auth.utils.UserMapper;
import com.consti.security.Auth.entity.Role;
import com.consti.security.Auth.repository.UserEntityRepository;


import com.consti.security.entity.Product;
import com.consti.security.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.time.Month.SEPTEMBER;


@Configuration
@RequiredArgsConstructor

public class ApplicationConfig {

    private final UserEntityRepository userEntityRepository;
    private final IProductRepository productRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userEntityRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("EL usuario: " + username + " no fue encontrado en la DB"));
    }

    @Bean
    public UserMapper studentMapper() {
        return new UserMapper();
    }

    @Bean

    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            UserEntity userAdmin = new UserEntity(
                    1,
                    "Yunior",
                    "Gonzalez",
                    "yu@gmail.com",
                    passwordEncoder().encode("1234567"),
                    LocalDate.of(2000, SEPTEMBER, 7),
                    0,
                    Role.ADMIN
            );
            userEntityRepository.save(userAdmin);


            List<Product> productList = new ArrayList<>();
            productList.add(new Product(
                    1,
                    "11111",
                    "Calzado",
                    "Zapatos Nike",
                    "Calzado comodo para correr y hacer deportes",
                    "azul, negro, blanco",
                    45.5,
                    99
            ));
            productList.add(new Product(
                    2,
                    "22222",
                    "Prendas",
                    "Camisa Polo",
                    "Camisa comoda y casual para cualquier ocasion",
                    "rojo, negro, blanco",
                    29.9,
                    99
            ));
            productList.add(new Product(
                    3,
                    "33333",
                    "Accesorios",
                    "Gorra",
                    "Gorra comoda para correr y hacer deportes",
                    "azul, negro, blanco",
                    18.9,
                    99
            ));
            productRepository.saveAll(productList);
        };
    }

}
