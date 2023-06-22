package com.kiraz.messengerapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GroupConversationDTO {
    private String name;
    private Set<GroupUserDTO> members;
}
