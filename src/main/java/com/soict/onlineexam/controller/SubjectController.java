package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.SubjectDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Subject;
import com.ngovangiang.onlineexam.entity.Topic;
import com.ngovangiang.onlineexam.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/subjects")
@Validated
public class SubjectController extends AbstractBaseController<Subject, Integer> {

    private SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        super(subjectService);
        this.subjectService = subjectService;
    }

    @GetMapping(params = {"code"})
    public List<Subject> searchSubjectsByCode(@RequestParam String code) {
        return subjectService.searchSubjectsByCode(code);
    }

    @GetMapping("/{subjectId}/classes")
    public List<Class> getClassesById(@PathVariable @Valid @NotNull @Min(1) Integer subjectId) {
        return subjectService.getClassesBySubjectId(subjectId);
    }

    @GetMapping("/{subjectId}/topics")
    public List<Topic> getTopicsById(@PathVariable @Valid @NotNull @Min(1) Integer subjectId) {
        return subjectService.getTopicsBySubjectId(subjectId);
    }

    @PostMapping
    public Subject createSubject(@RequestBody @Valid SubjectDTO subjectDTO) {
        return subjectService.createSubject(subjectDTO);
    }

    @PutMapping("/{subjectId}")
    public Subject updateSubject(@RequestBody @Valid SubjectDTO subjectDTO,
                                 @PathVariable Integer subjectId) {
        return subjectService.updateSubject(subjectDTO, subjectId);
    }
}
