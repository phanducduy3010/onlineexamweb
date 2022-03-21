package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.ClassDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Exam;
import com.ngovangiang.onlineexam.entity.Student;
import com.ngovangiang.onlineexam.entity.Subject;
import com.ngovangiang.onlineexam.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/classes")
@Validated
public class ClassController extends AbstractBaseController<Class, Integer> {

    private ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        super(classService);
        this.classService = classService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = {"code"})
    public List<Class> searchClasses(@RequestParam String code) {
        return classService.searchClassesByCode(code);
    }

    @GetMapping("/{classId}/students")
    public List<Student> getStudentsOfClass(@PathVariable @Valid @NotNull @Min(1) Integer classId) {
        return classService.getStudentsOfClass(classId);
    }

    @GetMapping("/{classId}/exams")
    public List<Exam> getExamsOfClass(@PathVariable @Valid @NotNull @Min(1) Integer classId) {
        return classService.getExamsOfClass(classId);
    }

    @GetMapping("/{classId}/subject")
    public Subject getSubjectOfClass(@PathVariable @Valid @NotNull @Min(1) Integer classId) {
        return classService.getSubjectOfClass(classId);
    }

    @PostMapping
    public Class createClass(@RequestBody @Valid ClassDTO classDTO) {
        return classService.createClass(classDTO);
    }

    @PutMapping("/{classId}")
    public Class updateClass(@RequestBody @Valid ClassDTO classDTO,
                             @PathVariable @Valid @NotNull @Min(1) Integer classId) {
        return classService.updateClass(classDTO, classId);
    }

}
