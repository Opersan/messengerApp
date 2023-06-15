package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.model.User;

public class UserConverter {
    public static User userConverter(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setHashedPassword(userDTO.getPassword());
        return user;
    }

    public static UserDTO userDTOConverter(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getHashedPassword());
        return userDTO;
    }
}
