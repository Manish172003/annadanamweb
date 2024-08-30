package com.donation.annadanam.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donation.annadanam.entities.Users;
import com.donation.annadanam.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {

//    @Autowired
//    private UserService userService;
//
//    @PostMapping
//    public ResponseEntity<Users> addUser(@RequestBody Users user) {
//        Users createdUser = userService.addUser(user);
//        return ResponseEntity.ok(createdUser);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users user) {
//        Users updatedUser = userService.updateUser(id, user);
//        return ResponseEntity.ok(updatedUser);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
//        Users user = userService.getUserById(id);
//        return ResponseEntity.ok(user);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Users>> getAllUsers() {
//        List<Users> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
}

