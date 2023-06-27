package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.converter.AccountConverter;
import com.kiraz.messengerapp.dto.*;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.model.Account;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getAllUsersExceptItself(String email){
        List<User> users = userRepository.findAllByEmailNot(email);
        return users;
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
        return userRepository.save(user);
    }

    public User saveUserByProfile(User user, Account account) {
        user.setAccount(account);
        return userRepository.save(user);
    }

    public void updateUserByProfile(User user) {
        User updatedUser = userRepository.findByEmail(user.getEmail()).get();

        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setImage(user.getImage());
        updatedUser.setCreatedAt(user.getCreatedAt());

        userRepository.save(updatedUser);
    }

    public User updateUserByUserDTO(User user) {
        User updatedUser = userRepository.findByEmail(user.getEmail()).get();

        updatedUser.setName(!user.getName().isEmpty() ? user.getName() : updatedUser.getName());
        updatedUser.setImage(!user.getImage().isEmpty() ? user.getImage() : updatedUser.getImage());

        return userRepository.save(updatedUser);
    }

    public User saveUserSilent(User user) {
        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
        return userRepository.save(user);
    }

    @Override
    public JwtUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ email));

        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new JwtUserDetails(user.getId(), user.getEmail(), user.getHashedPassword(), authorities);
    }
}
