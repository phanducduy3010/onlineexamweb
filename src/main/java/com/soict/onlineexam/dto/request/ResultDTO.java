package com.ngovangiang.onlineexam.dto.request;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Set;

@Value
public class ResultDTO {

    @NotNull
    @Min(1)
    Integer studentId;

    @NotNull
    @Min(1)
    Integer examId;

    @NotNull
    @Past
    LocalDateTime startAt;

    Set<@NotNull @Min(1) Integer> answerIds;
}
