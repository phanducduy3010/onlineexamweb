package com.ngovangiang.onlineexam.dto.request;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
public class ClassDTO {

    @NotBlank
    @Pattern(regexp = "[1-9][0-9]{5}")
    String code;

    @NotBlank
    String semeter;

    @Pattern(regexp = "THEORY|EXPERIMENT|PRACTICE")
    String classType;

    Integer teacherId;

    Integer subjectId;
}
