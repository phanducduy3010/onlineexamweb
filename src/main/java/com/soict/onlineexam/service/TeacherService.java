package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.dto.request.TeacherDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Teacher;

import java.util.List;

public interface TeacherService extends BaseService<Teacher, Integer> {

    List<Class> getTeachingClassesOfTeacher(Integer id);

    Teacher createTeacher(TeacherDTO teacherDTO, String password);

    Teacher updateTeacher(TeacherDTO teacherDTO, Integer teacherId);

    void deleteTeacher(Integer teacherId);
}
