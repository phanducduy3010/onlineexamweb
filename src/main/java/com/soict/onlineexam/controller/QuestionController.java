package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.QuestionDTO;
import com.ngovangiang.onlineexam.entity.Question;
import com.ngovangiang.onlineexam.service.BaseService;
import com.ngovangiang.onlineexam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/questions")
@Validated
public class QuestionController extends AbstractBaseController<Question, Integer> {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        super(questionService);
        this.questionService = questionService;
    }

    @GetMapping(params = {"topicName", "subjectCode", "subjectName"})
    public List<Question> getBySubjectAndTopicInfo(@RequestParam String topicName,
                                                   @RequestParam String subjectCode,
                                                   @RequestParam String subjectName) {
        return questionService.findByTopicAndSubjectInfo(topicName, subjectCode, subjectName);
    }

    @PostMapping
    public Question createQuestion(@RequestBody @Valid QuestionDTO questionDTO) {
        return questionService.createQuestion(questionDTO);
    }

    @PutMapping("/{questionId}")
    public Question updateQuestion(@RequestBody @Valid QuestionDTO questionDTO,
                                   @PathVariable @Valid @NotNull @Min(1) Integer questionId) {
        return questionService.updateQuestion(questionDTO, questionId);
    }
}
