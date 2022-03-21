package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.dto.request.QuestionDTO;
import com.ngovangiang.onlineexam.entity.Question;

import java.util.List;

public interface QuestionService extends BaseService<Question, Integer> {

    Question createQuestion(QuestionDTO questionDTO);

    Question updateQuestion(QuestionDTO questionDTO, Integer questionId);

    List<Question> findByTopicAndSubjectInfo(String topicName, String subjectCode, String subjectName);
}
