package com.kiraz.messengerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String name;
    private String email;
    private String picture;
    private String sub;
}
