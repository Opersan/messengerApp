package com.kiraz.messengerapp.controller;

import com.kiraz.messengerapp.annotation.JsonArg;
import com.kiraz.messengerapp.converter.AccountConverter;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.dto.*;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.service.AccountService;
import com.kiraz.messengerapp.service.JwtTokenService;
import com.kiraz.messengerapp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private UserService userService;
    private AccountService accountService;
    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;

    private UserConverter userConverter;

    private AccountConverter accountConverter;

    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService, AccountService accountService,
                          UserConverter userConverter, AccountConverter accountConverter) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.accountService = accountService;
        this.userConverter = userConverter;
        this.accountConverter = accountConverter;
    }
    @PostMapping("/save")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO user) {
        User newUser = userService.saveUser(userConverter.convertUserDTOtoUser(user));
        if(newUser != null) {
            return new ResponseEntity<>(userConverter.convertUserToUserDTO(newUser), HttpStatus.OK);
        } else {
            logger.error("");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/loginWithPwd")
    public ResponseEntity<UserLoginResponse> loginWithPwd(@Valid @RequestBody UserLoginRequest user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        }catch (final BadCredentialsException ex) {
            logger.error(String.valueOf(ex));
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        final UserLoginResponse userLoginResponse = userConverter.convertUserLoginRequestToUserLoginResponse(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenService.generateToken(userDetails))
                .body(userLoginResponse);
    }

    @PostMapping("/loginWithOAuth2")
    public ResponseEntity<UserLoginResponse> loginWithGoogle(@JsonArg("account") AccountDTO account,
                                             @JsonArg("profile") ProfileDTO profileDTO) {
        try {
            if (userService.getUserByEmail(profileDTO.getEmail()).isEmpty()) {
                userService.saveUserByProfile(userConverter.convertProfileToUser(profileDTO), accountConverter.convertAccountDTOtoAccount(account));
            } else {
                userService.updateUserByProfile(userConverter.convertProfileToUser(profileDTO));
            }
            if (accountService.getAccountByProviderId(account.getProviderAccountId()).isEmpty()) {
                accountService.saveAccountByAccountDTO(accountConverter.convertAccountDTOtoAccount(account));
            } else {
                accountService.updateAccountByAccountDTO(account);
            }
        } catch (final BadCredentialsException ex) {
            logger.error(String.valueOf(ex));
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userService.loadUserByUsername("");
        final UserLoginResponse userLoginResponse = new UserLoginResponse();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenService.generateToken(userDetails))
                .body(userLoginResponse);
    }
}
