package com.ngovangiang.onlineexam.service.impl;

import com.ngovangiang.onlineexam.dto.request.TeacherDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Teacher;
import com.ngovangiang.onlineexam.exception.ResourceNotFoundException;
import com.ngovangiang.onlineexam.repository.TeacherRepository;
import com.ngovangiang.onlineexam.service.AbstractBaseService;
import com.ngovangiang.onlineexam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl extends AbstractBaseService<Teacher, Integer>
        implements TeacherService {

    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        super(teacherRepository, Teacher.class);
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Class> getTeachingClassesOfTeacher(Integer id) {
        return teacherRepository.getById(id).getTeachingClasses();
    }

    @Override
    public Teacher createTeacher(TeacherDTO teacherDTO, String password) {
        Teacher newTeacher = new Teacher(teacherDTO.getEmail(), password, teacherDTO.getName());
        return teacherRepository.save(newTeacher);
    }

    @Override
    public Teacher updateTeacher(TeacherDTO teacherDTO, Integer teacherId) {
        return teacherRepository.findById(teacherId)
                .map(oldTeacher -> {
                    oldTeacher.setName(teacherDTO.getName());
                    oldTeacher.setEmail(teacherDTO.getEmail());

                    return teacherRepository.save(oldTeacher);
                })
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(teacherId, Teacher.class);
                });
    }

    @Override
    public void deleteTeacher(Integer teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}
