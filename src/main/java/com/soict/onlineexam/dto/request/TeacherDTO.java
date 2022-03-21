package com.ngovangiang.onlineexam.dto.request;

import lombok.Value;

@Value
public class TeacherDTO extends UserDTO {

    public TeacherDTO(String email, String name) {
        super(email, name);
    }
}
