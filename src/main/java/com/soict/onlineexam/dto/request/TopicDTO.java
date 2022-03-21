package com.ngovangiang.onlineexam.dto.request;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class TopicDTO {

    @NotBlank
    String name;

    String description;

    @NotNull
    @Min(1)
    Integer subjectId;
}
