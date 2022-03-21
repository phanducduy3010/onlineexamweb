package com.ngovangiang.onlineexam.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class QuestionDTO {

    Integer topicId;

    String content;

    List<AnswerDTO> answers;
}
