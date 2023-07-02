package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.*;
import com.kiraz.messengerapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mapping(target="password", source = "hashedPassword")
    UserDTO convertUserToUserDTO(User user);

    Set<UserDTO> convertUserListToUserDTOList(Set<User> users);

    Set<User> convertUserDTOListToUserList(Set<UserDTO> userDTOList);

    @Mapping(target="hashedPassword", source = "password")
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "conversations", ignore = true)
    @Mapping(target = "seenMessages", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "messages", ignore = true)
    User convertUserDTOtoUser(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "conversations", ignore = true)
    @Mapping(target = "seenMessages", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "messages", ignore = true)
    User convertUserRegisterRequestToUser(UserUpdateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target="image", source = "picture")
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "conversations", ignore = true)
    @Mapping(target = "seenMessages", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "messages", ignore = true)
    User convertProfileToUser(ProfileDTO profileDTO);

    UserLoginResponse convertUserLoginRequestToUserLoginResponse(UserLoginRequest request);

}
