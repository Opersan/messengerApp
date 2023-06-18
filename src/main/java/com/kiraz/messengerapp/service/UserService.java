package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.converter.AccountConverter;
import com.kiraz.messengerapp.dto.*;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.model.Account;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
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

    public UserDTO saveUser(UserDTO user) {
        User newUser = UserConverter.convertUserDTOtoUserConverter(user);
        newUser.setHashedPassword(passwordEncoder.encode(user.getPassword()));
        return UserConverter.convertUserDTOtoUserConverter(userRepository.save(newUser));
    }

    public ProfileDTO saveUserByProfile(ProfileDTO profileDTO, AccountDTO accountDTO) {
        User newUser = UserConverter.convertProfileToUser(profileDTO);
        newUser.setAccount(AccountConverter.convertAccountDTOtoAccount(accountDTO));
        return UserConverter.convertUserToProfileDTO(userRepository.save(newUser));
    }

    public void updateUserByProfile(ProfileDTO profileDTO) {
        User updatedUser = userRepository.findByEmail(profileDTO.getEmail()).get();

        updatedUser.setName(profileDTO.getName());
        updatedUser.setEmail(profileDTO.getEmail());
        updatedUser.setImage(profileDTO.getPicture());
        updatedUser.setCreatedAt(Instant.ofEpochMilli(profileDTO.getIat()));

        userRepository.save(updatedUser);
    }

    public UserRegisterOAuth2Response saveUserSilent(UserRegisterOAuth2Request user) {
        User newUser = UserConverter.convertUserRegisterRequestToUserConverter(user);
        newUser.setHashedPassword(passwordEncoder.encode(user.getPassword()));
        return UserConverter.convertUserToUserRegisterResponseConverter(userRepository.save(newUser));
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
