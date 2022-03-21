package com.ngovangiang.onlineexam.service.impl;

import com.ngovangiang.onlineexam.dto.request.ClassDTO;
import com.ngovangiang.onlineexam.entity.*;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.type.ClassType;
import com.ngovangiang.onlineexam.exception.ResourceNotFoundException;
import com.ngovangiang.onlineexam.repository.ClassReposiroty;
import com.ngovangiang.onlineexam.service.AbstractBaseService;
import com.ngovangiang.onlineexam.service.ClassService;
import com.ngovangiang.onlineexam.service.SubjectService;
import com.ngovangiang.onlineexam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClassServiceImpl extends AbstractBaseService<Class, Integer> implements ClassService {

    private ClassReposiroty classRepository;

    private TeacherService teacherService;

    private SubjectService subjectService;

    @Autowired
    public ClassServiceImpl(ClassReposiroty classReposiroty, TeacherService teacherService, SubjectService subjectService) {
        super(classReposiroty, Class.class);
        this.classRepository = classReposiroty;
        this.teacherService = teacherService;
        this.subjectService = subjectService;
    }

    @Override
    public List<Class> searchClassesByCode(String code) {
        return classRepository.findByCodeContains(code);
    }

    @Override
    public Class createClass(ClassDTO classDTO) {
        Teacher teacher = teacherService.getById(classDTO.getTeacherId());
        Subject subject = subjectService.getById(classDTO.getSubjectId());

        Class newClass = new Class(classDTO.getCode());
        newClass.setSemeter(classDTO.getSemeter());
        newClass.setType(ClassType.valueOf(classDTO.getClassType()));
        newClass.setTeacher(teacher);
        newClass.setSubject(subject);
        return classRepository.save(newClass);
    }

    @Override
    public Class updateClass(ClassDTO classDTO, Integer classId) {
        return classRepository.findById(classId)
                .map(oldClass -> {
                    Teacher teacher = teacherService.getById(classDTO.getTeacherId());
                    Subject subject = subjectService.getById(classDTO.getSubjectId());

                    oldClass.setCode(classDTO.getCode());
                    oldClass.setSemeter(classDTO.getSemeter());
                    oldClass.setType(ClassType.valueOf(classDTO.getClassType()));
                    oldClass.setTeacher(teacher);
                    oldClass.setSubject(subject);

                    return classRepository.save(oldClass);
                })
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(classId, Class.class);
                });
    }

    @Override
    public List<Student> getStudentsOfClass(Integer classId) {
        return getById(classId).getLearningStudents();
    }

    @Override
    public List<Exam> getExamsOfClass(Integer classId) {
        return getById(classId).getExams();
    }

    @Override
    public Subject getSubjectOfClass(Integer classId) {
        return subjectService.getSubjectByClassId(classId);
    }
}
