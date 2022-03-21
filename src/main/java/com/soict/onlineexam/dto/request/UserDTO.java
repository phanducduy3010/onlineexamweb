package com.ngovangiang.onlineexam.dto.request;

import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class UserDTO {

    @NotNull
    @Email
    private final String email;

    @NotBlank
    private final String name;

    public UserDTO(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
