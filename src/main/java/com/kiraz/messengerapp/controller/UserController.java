package com.kiraz.messengerapp.controller;

import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/userExceptItself")
    public List<UserDTO> getAllUsersExceptItself(@RequestParam(value="email") String email) {
        List<UserDTO> users = UserConverter.convertUserToUserDTOList(userService.getAllUsersExceptItself(email));
        if (users.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return users;
    }

    public User getUserById( Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/userById")
    public UserDTO getUserDTOById(@RequestParam(value="id") Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return UserConverter.convertUserToUserDTO(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/userByEmail")
    public UserDTO getUserByEmail(@RequestParam(value="email") String email) {
        Optional<User> user = null;
        if (!email.isEmpty()) {
            user = userService.getUserByEmail(email);
        }
        if (user.isPresent()) {
            return UserConverter.convertUserToUserDTO(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
