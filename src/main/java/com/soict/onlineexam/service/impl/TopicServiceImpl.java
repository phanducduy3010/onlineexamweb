package com.ngovangiang.onlineexam.service.impl;

import com.ngovangiang.onlineexam.dto.request.TopicDTO;
import com.ngovangiang.onlineexam.entity.Question;
import com.ngovangiang.onlineexam.entity.Topic;
import com.ngovangiang.onlineexam.exception.ResourceNotFoundException;
import com.ngovangiang.onlineexam.repository.TopicRepository;
import com.ngovangiang.onlineexam.service.AbstractBaseService;
import com.ngovangiang.onlineexam.service.SubjectService;
import com.ngovangiang.onlineexam.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl extends AbstractBaseService<Topic, Integer> implements TopicService {

    private TopicRepository topicRepository;

    private SubjectService subjectService;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, SubjectService subjectService) {
        super(topicRepository, Topic.class);
        this.topicRepository = topicRepository;
        this.subjectService = subjectService;
    }

    @Override
    public List<Topic> searchByName(String partOfName) {
        return topicRepository.findByNameContainsIgnoreCase(partOfName);
    }

    @Override
    public List<Question> getQuestionsByTopicId(Integer topicId) {
        return getById(topicId).getQuestions();
    }

    @Override
    public Topic createTopic(TopicDTO topicDTO) {
        Topic newTopic = new Topic(topicDTO.getName());
        newTopic.setDescription(topicDTO.getDescription());
        newTopic.setSubject(subjectService.getById(topicDTO.getSubjectId()));
        return topicRepository.save(newTopic);
    }

    @Override
    public Topic updateTopic(TopicDTO topicDTO, Integer topicId) {
        return topicRepository.findById(topicId)
                .map(oldTopic -> {
                    oldTopic.setSubject(subjectService.getById(topicDTO.getSubjectId()));
                    oldTopic.setName(topicDTO.getName());
                    oldTopic.setDescription(topicDTO.getDescription());

                    return topicRepository.save(oldTopic);
                })
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(topicId, Topic.class);
                });
    }

}
