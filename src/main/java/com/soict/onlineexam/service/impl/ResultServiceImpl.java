package com.ngovangiang.onlineexam.service.impl;

import com.ngovangiang.onlineexam.dto.request.ResultDTO;
import com.ngovangiang.onlineexam.entity.Exam;
import com.ngovangiang.onlineexam.entity.Result;
import com.ngovangiang.onlineexam.entity.Student;
import com.ngovangiang.onlineexam.repository.AnswerRepository;
import com.ngovangiang.onlineexam.repository.ExamRepository;
import com.ngovangiang.onlineexam.repository.ResultReposiroty;
import com.ngovangiang.onlineexam.repository.StudentRepository;
import com.ngovangiang.onlineexam.service.AbstractBaseService;
import com.ngovangiang.onlineexam.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResultServiceImpl extends AbstractBaseService<Result, Integer> implements ResultService {

    private ResultReposiroty resultReposiroty;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    public ResultServiceImpl(ResultReposiroty resultReposiroty) {
        super(resultReposiroty, Result.class);
        this.resultReposiroty = resultReposiroty;
    }

    @Override
    public Result handleResultSubmission(ResultDTO resultDTO) {
        Result newResult = new Result();
        newResult.setStartAt(resultDTO.getStartAt());
        newResult.setSubmitAt(LocalDateTime.now());

        Student student = studentRepository.getById(resultDTO.getStudentId());
        newResult.setStudent(student);

        Exam exam = examRepository.getById(resultDTO.getExamId());
        newResult.setExam(exam);
        resultReposiroty.save(newResult);

        System.out.println(resultDTO.getAnswerIds());

        resultDTO.getAnswerIds().forEach(answerId -> {
            newResult.addAnswer(answerRepository.getById(answerId));
        });

        System.out.println(newResult.getAnswers().size());
        resultReposiroty.save(newResult);
        Integer resultId = newResult.getId();
        newResult.setScore(answerRepository.countByResultsIdAndCorrectIsTrue(resultId));
        resultReposiroty.save(newResult);

        return newResult;
    }

}
