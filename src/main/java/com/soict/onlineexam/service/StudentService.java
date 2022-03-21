package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.dto.request.StudentDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Student;

import java.util.List;

public interface StudentService extends BaseService<Student, Integer> {

    List<Student> searchStudentsByCode(String partOfCode);

    List<Class> getLearningClassesOfStudent(Integer id);

    Student createStudent(StudentDTO studentDTO, String password);

    Student updateStudent(StudentDTO newValue, Integer id);
}