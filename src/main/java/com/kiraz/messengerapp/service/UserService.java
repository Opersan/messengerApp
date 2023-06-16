package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.dto.JwtUserDetails;
import com.kiraz.messengerapp.dto.UserRegisterOAuth2Request;
import com.kiraz.messengerapp.dto.UserRegisterOAuth2Response;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public UserDTO saveUser(UserDTO user) {
        User newUser = UserConverter.userDTOtoUserConverter(user);
        newUser.setHashedPassword(passwordEncoder.encode(user.getPassword()));
        return UserConverter.userDTOtoUserConverter(userRepository.save(newUser));
    }

    public UserRegisterOAuth2Response saveUserSilent(UserRegisterOAuth2Request user) {
        User newUser = UserConverter.userRegisterRequestToUserConverter(user);
        newUser.setHashedPassword(passwordEncoder.encode(user.getPassword()));
        return UserConverter.userToUserRegisterResponseConverter(userRepository.save(newUser));
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
