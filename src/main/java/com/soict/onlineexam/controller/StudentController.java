package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.StudentDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Student;
import com.ngovangiang.onlineexam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/students")
@Validated
public class StudentController extends AbstractBaseController<Student, Integer> {

    private StudentService studentService;

    private ValidatorFactory validatorFactory;

    @Autowired
    public StudentController(StudentService studentService, ValidatorFactory validatorFactory) {
        super(studentService);
        this.studentService = studentService;
        this.validatorFactory = validatorFactory;
    }

    @GetMapping(params = {"code"})
    public List<Student> searchStudents(@RequestParam String code) {
        return studentService.searchStudentsByCode(code);
    }

    @GetMapping("/{studentId}/classes")
    public List<Class> getLearningClassesOfStudent(
            @PathVariable @Valid @NotNull @Min(1) Integer studentId
    ) {
        return studentService.getLearningClassesOfStudent(studentId);
    }

    @PostMapping
    public Student createStudent(@RequestBody Map<String, String> studentAttrs) {
        Validator validator = validatorFactory.getValidator();
        StudentDTO studentDTO = new StudentDTO(
                studentAttrs.get("email"),
                studentAttrs.get("name"),
                studentAttrs.get("code")
        );

        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentDTO);
        if (violations.size() > 0) throw new ConstraintViolationException(violations);

        String password = studentAttrs.get("password");
        if (password == null || password.isBlank() || password.length() < 8)
            throw new IllegalArgumentException("Password must be not blank and has length greater than or equals 8!");

        return studentService.createStudent(studentDTO, password);
    }

    @PutMapping("/{studentId}")
    public Student updateStudent(@RequestBody @Valid StudentDTO newValue,
                                 @PathVariable @Valid @NotNull @Min(1) Integer studentId) {
        return studentService.updateStudent(newValue, studentId);
    }

}
