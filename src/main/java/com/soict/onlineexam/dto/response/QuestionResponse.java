package com.ngovangiang.onlineexam.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngovangiang.onlineexam.entity.Answer;
import com.ngovangiang.onlineexam.entity.Topic;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionResponse {

    public QuestionResponse(Integer id, String content, Topic topic, List<Answer> answers) {
        this.id = id;
        this.content = content;
        this.topic = topic;
        this.answers = answers;
    }

    private Integer id;
    private String content;
    private Topic topic;
    private List<Answer> answers = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
