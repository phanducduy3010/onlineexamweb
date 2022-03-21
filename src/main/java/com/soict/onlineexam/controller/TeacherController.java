package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.TeacherDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Teacher;
import com.ngovangiang.onlineexam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/teachers")
@Validated
public class TeacherController extends AbstractBaseController<Teacher, Integer> {

    private TeacherService teacherService;

    private ValidatorFactory validatorFactory;

    @Autowired
    public TeacherController(TeacherService teacherService, ValidatorFactory validatorFactory) {
        super(teacherService);
        this.teacherService = teacherService;
        this.validatorFactory = validatorFactory;
    }

    @GetMapping("/{teacherId}/classes")
    public List<Class> getTeachingClassesOfTeacher(@PathVariable @Valid @NotNull @Min(1) Integer teacherId) {
        return teacherService.getTeachingClassesOfTeacher(teacherId);
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Map<String, String> teacherAttrs) {
        Validator validator = validatorFactory.getValidator();
        TeacherDTO teacherDTO = new TeacherDTO(teacherAttrs.get("email"), teacherAttrs.get("name"));

        Set<ConstraintViolation<TeacherDTO>> violations = validator.validate(teacherDTO);
        if (violations.size() > 0) throw new ConstraintViolationException(violations);

        String password = teacherAttrs.get("password");
        if (password == null || password.isBlank() || password.length() < 8)
            throw new IllegalArgumentException("Password must be not blank and has length greater than or equals 8!");

        return teacherService.createTeacher(teacherDTO, password);
    }

    @PutMapping("/{teacherId}")
    public Teacher updateTeacher(
            @RequestBody @Valid TeacherDTO teacherDTO,
            @PathVariable @Valid @NotNull @Min(1) Integer teacherId
    ) {
        return teacherService.updateTeacher(teacherDTO, teacherId);
    }

    @DeleteMapping("/{teacherId}")
    public void deleteTeacher(@PathVariable @Valid @NotNull @Min(1) Integer teacherId) {
        teacherService.deleteTeacher(teacherId);
    }
}
