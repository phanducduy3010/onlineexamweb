package com.ngovangiang.onlineexam.dto.request;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
public class SubjectDTO {

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}[0-9]{4}")
    String code;

    @NotBlank
    String name;
}
