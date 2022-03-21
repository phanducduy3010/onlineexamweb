package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.dto.request.TopicDTO;
import com.ngovangiang.onlineexam.entity.Question;
import com.ngovangiang.onlineexam.entity.Topic;

import java.util.List;

public interface TopicService extends BaseService<Topic, Integer> {

    List<Topic> searchByName(String partOfName);

    List<Question> getQuestionsByTopicId(Integer topicId);

    Topic createTopic(TopicDTO topicDTO);

    Topic updateTopic(TopicDTO topicDTO, Integer topicId);
}
