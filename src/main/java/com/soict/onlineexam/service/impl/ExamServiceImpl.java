package com.ngovangiang.onlineexam.service.impl;

import com.ngovangiang.onlineexam.dto.request.ExamDTO;
import com.ngovangiang.onlineexam.entity.*;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.exception.NotEnoughQuestionException;
import com.ngovangiang.onlineexam.exception.ResourceNotFoundException;
import com.ngovangiang.onlineexam.repository.ExamRepository;
import com.ngovangiang.onlineexam.repository.TopicRepository;
import com.ngovangiang.onlineexam.service.AbstractBaseService;
import com.ngovangiang.onlineexam.service.ClassService;
import com.ngovangiang.onlineexam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamServiceImpl extends AbstractBaseService<Exam, Integer> implements ExamService {

    private ExamRepository examRepository;

    private TopicRepository topicRepository;

    private ClassService classService;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository, TopicRepository topicRepository, ClassService classService) {
        super(examRepository, Exam.class);
        this.examRepository = examRepository;
        this.topicRepository = topicRepository;
        this.classService = classService;
    }

    @Override
    public Exam createExam(ExamDTO examDTO) {
        Exam newExam = new Exam(examDTO.getName(), examDTO.getStartAt(), examDTO.getFinishAt());
        newExam.setIsOpen(examDTO.isOpen());
        newExam.setOwnerClass(classService.getById(examDTO.getOwnClassId()));

        return setExamTopicsAndSave(examDTO, newExam);
    }

    @Override
    public Exam updateExam(ExamDTO examDTO, Integer examId) {
        Exam oldExam = getById(examId);
        oldExam.setName(examDTO.getName());
        oldExam.setStartAt(examDTO.getStartAt());
        oldExam.setFinishAt(examDTO.getFinishAt());
        oldExam.setIsOpen(examDTO.isOpen());
        oldExam.getTopics().clear();

        return setExamTopicsAndSave(examDTO, oldExam);
    }

    private Exam setExamTopicsAndSave(ExamDTO examDTO, Exam oldExam) {
        Map<Integer, Integer> examTopics = examDTO.getExamTopics();
        List<Topic> topics = topicRepository.findAllById(examTopics.keySet());
        topics.forEach(topic -> {
            oldExam.addTopic(topic, examTopics.get(topic.getId()));
        });

        return examRepository.save(oldExam);
    }

    @Override
    public Set<Question> getQuestionsOfExam(Integer examId) {

        Exam exam = getById(examId);

        Set<Question> randomSelectedQuestions = new HashSet<>();
        Random random = new Random();

        for (ExamTopic examTopic : exam.getTopics()) {
            Integer numOfQuestions = examTopic.getNumberOfQuestions();
            List<Question> questions = examTopic.getTopic().getQuestions();

            if (questions.size() < numOfQuestions)
                throw new NotEnoughQuestionException(examTopic.getTopic().getName(), numOfQuestions);

            Set<Question> selectedQuestionsOfTopic = new HashSet<>(numOfQuestions);
            while (selectedQuestionsOfTopic.size() < numOfQuestions) {
                int index = random.nextInt(questions.size());
                selectedQuestionsOfTopic.add(questions.get(index));
            }

            randomSelectedQuestions.addAll(selectedQuestionsOfTopic);
        }

        return randomSelectedQuestions;
    }

}
