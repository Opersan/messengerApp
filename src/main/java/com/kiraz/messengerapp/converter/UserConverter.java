package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.dto.UserRegisterOAuth2Request;
import com.kiraz.messengerapp.dto.UserRegisterOAuth2Response;
import com.kiraz.messengerapp.model.User;

public class UserConverter {
    public static User userDTOtoUserConverter(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setHashedPassword(userDTO.getPassword());
        return user;
    }

    public static UserDTO userDTOtoUserConverter(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getHashedPassword());
        return userDTO;
    }

    public static User userRegisterRequestToUserConverter(UserRegisterOAuth2Request request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setHashedPassword(request.getPassword());
        return user;
    }

    public static UserRegisterOAuth2Response userToUserRegisterResponseConverter(User user){
        UserRegisterOAuth2Response response = new UserRegisterOAuth2Response();
        response.setEmail(user.getEmail());
        return response;
    }
}
