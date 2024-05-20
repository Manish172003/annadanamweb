package com.donation.annadanam.services;


import java.util.List;

import com.donation.annadanam.entities.User;

public interface UserService {
    User addUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getUserById(Long id);
    List<User> getAllUsers();
}
