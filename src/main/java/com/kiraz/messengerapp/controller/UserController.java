package com.kiraz.messengerapp.controller;

import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.dto.UserUpdateRequest;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {

    private UserService userService;

    private UserConverter userConverter;

    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/userExceptItself")
    public ResponseEntity<Set<UserDTO>> getAllUsersExceptItself(@RequestParam(value="email") String email) {
        Set<UserDTO> users = userConverter.convertUserListToUserDTOList(new HashSet<>(userService.getAllUsersExceptItself(email)));
        if (users.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public User getUserById( Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody UserUpdateRequest request) {
        System.out.println(request.getName());
        UserDTO user = userConverter.convertUserToUserDTO(userService.updateUserByUserDTO(userConverter.convertUserRegisterRequestToUser(request)));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/userById")
    public ResponseEntity<UserDTO> getUserDTOById(@RequestParam(value="id") Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(userConverter.convertUserToUserDTO(user.get()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/userByEmail")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam(value="email") String email) {
        Optional<User> user = null;
        if (!email.isEmpty()) {
            user = userService.getUserByEmail(email);
        }
        if (user.isPresent()) {
            return new ResponseEntity<>(userConverter.convertUserToUserDTO(user.get()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

}
