package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.ExamDTO;
import com.ngovangiang.onlineexam.entity.Exam;
import com.ngovangiang.onlineexam.entity.Question;
import com.ngovangiang.onlineexam.exception.NotEnoughQuestionException;
import com.ngovangiang.onlineexam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/exams")
public class ExamController extends AbstractBaseController<Exam, Integer> {

    private ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        super(examService);
        this.examService = examService;
    }

//    @GetMapping("/{examId}/questions")
//    public ResponseEntity<Object> getQuestionsOfExam(@PathVariable Integer examId) {
//        try {
//            return ResponseEntity.ok(examService.getQuestionsOfExam(examId));
//        } catch (NotEnoughQuestionException ex) {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
//                    .body(ex.getMessage());
//        }
//    }

    @GetMapping("/{examId}/questions")
    public Set<Question> getQuestionsOfExam(@PathVariable Integer examId) {
        return examService.getQuestionsOfExam(examId);
    }

    @PostMapping
    public Exam createExam(@RequestBody ExamDTO examDTO) {
        return examService.createExam(examDTO);
    }

    @PutMapping("/{examId}")
    public Exam updateExam(@RequestBody ExamDTO examDTO, @PathVariable Integer examId) {
        return examService.updateExam(examDTO, examId);
    }

}
