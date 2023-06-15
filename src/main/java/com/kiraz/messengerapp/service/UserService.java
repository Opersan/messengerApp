package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.exception.EmailNotFoundException;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.UserRepository;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
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
        newUser.setHashedPassword(passwordEncoder.encode(user.getPassword()));
        return UserConverter.userDTOConverter(userRepository.save(newUser));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ email));

        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("IS_AUTHENTICATED_FULLY"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getHashedPassword(), authorities);
    }
}
