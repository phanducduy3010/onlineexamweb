package com.ngovangiang.onlineexam.dto.request;

import lombok.Value;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Map;

@Value
public class ExamDTO {

    @Positive
    Integer ownClassId;

    @NotBlank
    String name;

    @Past
    LocalDateTime startAt;


    LocalDateTime finishAt;

    boolean open;

    Map<@NotNull @Min(1) Integer,@NotNull @Min(1) Integer> examTopics;
}
