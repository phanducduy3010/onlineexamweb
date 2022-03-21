package com.ngovangiang.onlineexam.service.impl;

import com.ngovangiang.onlineexam.dto.request.QuestionDTO;
import com.ngovangiang.onlineexam.entity.Answer;
import com.ngovangiang.onlineexam.entity.Question;
import com.ngovangiang.onlineexam.entity.Topic;
import com.ngovangiang.onlineexam.exception.ResourceNotFoundException;
import com.ngovangiang.onlineexam.repository.QuestionRepository;
import com.ngovangiang.onlineexam.service.AbstractBaseService;
import com.ngovangiang.onlineexam.service.QuestionService;
import com.ngovangiang.onlineexam.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl extends AbstractBaseService<Question, Integer> implements QuestionService {

    private QuestionRepository questionRepository;

    private TopicService topicService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, TopicService topicService) {
        super(questionRepository, Question.class);
        this.questionRepository = questionRepository;
        this.topicService = topicService;
    }

    @Override
    public Question createQuestion(QuestionDTO questionDTO) {
        System.out.println(questionDTO.getTopicId());
        Topic ownTopic = topicService.getById(questionDTO.getTopicId());

        Question question = new Question(questionDTO.getContent());
        question.setTopic(ownTopic);

        questionDTO.getAnswers().forEach(answerDTO -> {
            Answer answer = new Answer(answerDTO.getContent(), answerDTO.isTrue());
            question.addAnswer(answer);
        });

        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(QuestionDTO questionDTO, Integer questionId) {
        Topic ownTopic = topicService.getById(questionDTO.getTopicId());

        return questionRepository.findById(questionId)
                .map(oldQuestion -> {
                    oldQuestion.setTopic(ownTopic);
                    oldQuestion.setContent(questionDTO.getContent());
                    oldQuestion.getAnswers().clear();

                    questionDTO.getAnswers().forEach(answerDTO -> {
                        Answer answer = new Answer(answerDTO.getContent(), answerDTO.isTrue());
                        oldQuestion.addAnswer(answer);
                    });

                    return questionRepository.save(oldQuestion);
                })
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(questionId, Question.class);
                });
    }

    @Override
    public List<Question> findByTopicAndSubjectInfo(String topicName, String subjectCode, String subjectName) {
        return questionRepository.findBySubjectAndTopicInfo(topicName, subjectCode, subjectName);
    }

}
