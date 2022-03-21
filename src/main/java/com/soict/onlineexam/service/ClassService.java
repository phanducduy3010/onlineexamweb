package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.dto.request.ClassDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Exam;
import com.ngovangiang.onlineexam.entity.Student;
import com.ngovangiang.onlineexam.entity.Subject;

import java.util.List;

public interface ClassService extends BaseService<Class, Integer> {

    public List<Class> searchClassesByCode(String code);

    Class createClass(ClassDTO classDTO);

    Class updateClass(ClassDTO classDTO, Integer classId);

    List<Student> getStudentsOfClass(Integer classId);

    List<Exam> getExamsOfClass(Integer classId);

    Subject getSubjectOfClass(Integer classId);
}
