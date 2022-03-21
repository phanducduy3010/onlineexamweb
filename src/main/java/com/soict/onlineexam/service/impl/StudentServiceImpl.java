package com.ngovangiang.onlineexam.service.impl;

import com.ngovangiang.onlineexam.dto.request.StudentDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Student;
import com.ngovangiang.onlineexam.exception.ResourceNotFoundException;
import com.ngovangiang.onlineexam.repository.StudentRepository;
import com.ngovangiang.onlineexam.service.AbstractBaseService;
import com.ngovangiang.onlineexam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends AbstractBaseService<Student, Integer> implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        super(studentRepository, Student.class);
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> searchStudentsByCode(String partOfCode) {
        return studentRepository.findByCodeContains(partOfCode);
    }

    @Override
    public List<Class> getLearningClassesOfStudent(Integer id) {
//        return studentRepository.getById(id).getLearningClasses();
        Student student =  studentRepository.findByIdWithClasses(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(id, Student.class);
                });

        return student.getLearningClasses();
    }

    public Student createStudent(StudentDTO studentDTO, String password) {
        Student newStudent = new Student(
                studentDTO.getEmail(),
                password,
                studentDTO.getName(),
                studentDTO.getCode()
        );

        return studentRepository.save(newStudent);
    }

    public Student updateStudent(StudentDTO newValue, Integer studentId) {
        return studentRepository.findById(studentId)
                .map(oldStudent -> {
                    oldStudent.setName(newValue.getName());
                    oldStudent.setEmail(newValue.getEmail());
                    oldStudent.setCode(newValue.getCode());

                    return studentRepository.save(oldStudent);
                })
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(studentId, Student.class);
                });
    }
}
