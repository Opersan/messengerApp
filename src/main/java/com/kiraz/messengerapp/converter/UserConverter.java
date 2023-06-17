package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.ProfileDTO;
import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.dto.UserRegisterOAuth2Request;
import com.kiraz.messengerapp.dto.UserRegisterOAuth2Response;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.utils.DateUtils;

import java.time.Instant;

public class UserConverter {
    public static User convertUserDTOtoUserConverter(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setHashedPassword(userDTO.getPassword());
        return user;
    }

    public static UserDTO convertUserDTOtoUserConverter(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getHashedPassword());
        return userDTO;
    }

    public static User convertUserRegisterRequestToUserConverter(UserRegisterOAuth2Request request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setHashedPassword(request.getPassword());
        return user;
    }

    public static UserRegisterOAuth2Response convertUserToUserRegisterResponseConverter(User user){
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
