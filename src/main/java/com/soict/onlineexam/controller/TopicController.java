package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.TopicDTO;
import com.ngovangiang.onlineexam.entity.Question;
import com.ngovangiang.onlineexam.entity.Subject;
import com.ngovangiang.onlineexam.entity.Topic;
import com.ngovangiang.onlineexam.service.TopicService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/topics")
@Validated
public class TopicController extends AbstractBaseController<Topic, Integer> {

    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        super(topicService);
        this.topicService = topicService;
    }

    @GetMapping(params = {"name"})
    public List<Topic> searchTopicsByName(@RequestParam String name) {
        return topicService.searchByName(name);
    }

    @GetMapping("/{topicId}/questions")
    public List<Question> getQuestionsById(@PathVariable @NonNull @Min(1) Integer topicId) {
        return topicService.getQuestionsByTopicId(topicId);
    }

    @GetMapping("/{topicId}/subject")
    public Subject getSubjectByTopicId(@PathVariable Integer topicId) {
        Subject result = topicService.getById(topicId).getSubject();
        return result;
    }

    @PostMapping
    public Topic createTopic(@RequestBody TopicDTO topicDTO) {
        return topicService.createTopic(topicDTO);
    }

    @PutMapping("/{topicId}")
    public Topic updateTopic(@RequestBody TopicDTO topicDTO,@PathVariable Integer topicId) {
        return topicService.updateTopic(topicDTO, topicId);
    }
}
