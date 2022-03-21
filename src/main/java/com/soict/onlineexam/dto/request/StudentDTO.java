package com.ngovangiang.onlineexam.dto.request;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
public class StudentDTO extends UserDTO{

    @NotBlank
    @Pattern(regexp = "[1-9][0-9]{7}")
    String code;

    public StudentDTO(String email, String name, String code) {
        super(email, name);
        this.code = code;
    }
}
