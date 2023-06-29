package com.kiraz.messengerapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginResponse {
    private String email;
    private String password;
    private String accessToken;
    private String image;
}
