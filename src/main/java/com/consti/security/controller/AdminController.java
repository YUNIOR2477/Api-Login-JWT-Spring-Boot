package com.consti.security.controller;

import com.consti.security.Auth.controller.dto.UserDTO;
import com.consti.security.Auth.controller.request.RegisterRequest;
import com.consti.security.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/user-control")
@CrossOrigin(origins = "*")
public class AdminController {
    private final UsersService usersService;

    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> String() {
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return usersService.getUsers();
    }

    @PatchMapping(path = "{userId}")
    public ResponseEntity<?> changeStudentRole(@PathVariable("userId") Integer userId) {
        usersService.changeUserRole(userId);
        return ResponseEntity.ok("Rol de usuario actualizado correctamente");
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<String> updateStudent(
            @PathVariable("userId") Integer userId,
            @Valid @RequestBody RegisterRequest update) {
        usersService.updateUser(userId, update);
        return ResponseEntity.ok("usuario actualizado correctamente");
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("userId") Integer userId) {
        usersService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
