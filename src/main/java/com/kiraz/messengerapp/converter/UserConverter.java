package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.*;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.utils.DateUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserConverter {

    public static UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setImage(user.getImage());

        return userDTO;
    }

    public static Set<UserDTO> convertUserListToUserDTOList(Set<User> users){
        Set<UserDTO> usersDTOList = new HashSet<>();
        for (User user: users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setImage(user.getImage());
            usersDTOList.add(userDTO);
        }

        return usersDTOList;
    }

    public static Set<User> convertUserDTOListToUserList(Set<UserDTO> userDTOList) {
        Set<User> userList = new HashSet<>();
        for (UserDTO userDTO: userDTOList) {
            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setImage(userDTO.getImage());
            userList.add(user);
        }

        return userList;
    }
    public static User convertUserDTOtoUser(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setHashedPassword(userDTO.getPassword());
        return user;
    }

    public static UserDTO convertUsertoUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getHashedPassword());
        return userDTO;
    }

    public static User convertUserRegisterRequestToUser(UserRegisterOAuth2Request request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setHashedPassword(request.getPassword());
        return user;
    }

    public static UserRegisterOAuth2Response convertUserToUserRegisterResponse(User user){
        UserRegisterOAuth2Response response = new UserRegisterOAuth2Response();
        response.setEmail(user.getEmail());
        return response;
    }

    public static User convertProfileToUser(ProfileDTO profileDTO) {
        User user = new User();
        user.setName(profileDTO.getName());
        user.setEmail(profileDTO.getEmail());
        user.setImage(profileDTO.getPicture());
        user.setCreatedAt(Instant.ofEpochMilli(profileDTO.getIat()));

        return user;
    }

    public static ProfileDTO convertUserToProfileDTO(User user) {
        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setName(user.getName());
        profileDTO.setEmail(user.getEmail());
        profileDTO.setPicture(user.getImage());

        return profileDTO;
    }
}
