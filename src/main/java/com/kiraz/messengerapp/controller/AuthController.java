package com.kiraz.messengerapp.controller;

import com.kiraz.messengerapp.annotation.JsonArg;
import com.kiraz.messengerapp.dto.*;
import com.kiraz.messengerapp.service.AccountService;
import com.kiraz.messengerapp.service.JwtTokenService;
import com.kiraz.messengerapp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private UserService userService;
    private AccountService accountService;
    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService, AccountService accountService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.accountService = accountService;
    }
    @PostMapping("/save")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO user) {
        UserDTO newUser = userService.saveUser(user);
        if(newUser != null) {
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/loginWithPwd")
    public UserLoginResponse loginWithPwd(@Valid @RequestBody UserLoginRequest user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        }catch (final BadCredentialsException ex) {
            logger.error(String.valueOf(ex));
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        final UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setAccessToken(jwtTokenService.generateToken(userDetails));

        return userLoginResponse;
    }

    @PostMapping("/loginWithOAuth2")
    public UserLoginResponse loginWithGoogle(@JsonArg("token") TokenDTO tokenDTO,
                                             @JsonArg("account") AccountDTO account, @JsonArg("profile") ProfileDTO profileDTO) {
        try {
            if (userService.getUserByEmail(profileDTO.getEmail()).isEmpty()) {
                userService.saveUserByProfile(profileDTO, account);
            } else {
                userService.updateUserByProfile(profileDTO, account);
            }
            if (accountService.getAccountByProviderId(account.getProviderAccountId()).isEmpty()) {
                accountService.saveAccountByAccountDTO(account);
            } else {
                accountService.updateAccountByAccountDTO(account);
            }
        } catch (final BadCredentialsException ex) {
            logger.error(String.valueOf(ex));
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userService.loadUserByUsername("");
        final UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setAccessToken(jwtTokenService.generateToken(userDetails));

        return userLoginResponse;
    }
}
