package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public UserDTO saveUser(UserDTO user) {
        User newUser = UserConverter.userConverter(user);
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
        newUser.setHashedPassword(passwordEncoder.encode(user.getPassword()));
        return UserConverter.userDTOConverter(userRepository.save(newUser));
    }
}
