package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.dto.request.ExamDTO;
import com.ngovangiang.onlineexam.entity.Exam;
import com.ngovangiang.onlineexam.entity.Question;

import java.util.List;
import java.util.Set;

public interface ExamService extends BaseService<Exam, Integer> {

    Exam createExam(ExamDTO examDTO);

    Exam updateExam(ExamDTO examDTO, Integer examId);

    Set<Question> getQuestionsOfExam(Integer examId);
}
