package com.consti.security.controller;

import com.consti.security.Auth.controller.dto.UserDTO;
import com.consti.security.service.UsersService;
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
    public void changeStudentRole(@PathVariable("userId") Integer userId) {
        usersService.changeUserRole(userId);
    }

    @PutMapping(path = "{userId}")
    public void updateStudent(
            @PathVariable("userId") Integer userId,
            @RequestParam(name = "lastname", required = false) String lastname,
            @RequestParam(name = "firstname", required = false) String firstname,
            @RequestParam(name = "email", required = false) String email) {
        usersService.updateUser(userId, lastname, firstname, email);
    }
    @DeleteMapping(path = "{userId}")
    public void deleteStudent(@PathVariable("userId") Integer userId) {
        usersService.deleteUser(userId);
    }
}
