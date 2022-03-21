package com.ngovangiang.onlineexam.service.impl;

import com.ngovangiang.onlineexam.dto.request.SubjectDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Student;
import com.ngovangiang.onlineexam.entity.Subject;
import com.ngovangiang.onlineexam.entity.Topic;
import com.ngovangiang.onlineexam.exception.ResourceNotFoundException;
import com.ngovangiang.onlineexam.repository.SubjectRepository;
import com.ngovangiang.onlineexam.service.AbstractBaseService;
import com.ngovangiang.onlineexam.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl extends AbstractBaseService<Subject, Integer> implements SubjectService {

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        super(subjectRepository, Subject.class);
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> searchSubjectsByCode(String partOfCode) {
        return subjectRepository.findByCodeContainsIgnoreCase(partOfCode);
    }

    @Override
    public List<Class> getClassesBySubjectId(Integer id) {
        return getById(id).getClasses();
    }

    @Override
    public List<Topic> getTopicsBySubjectId(Integer id) {
        return getById(id).getTopics();
    }

    @Override
    public Subject createSubject(SubjectDTO subjectDTO) {
        Subject newSubject = new Subject(subjectDTO.getCode(), subjectDTO.getName());
        return subjectRepository.save(newSubject);
    }

    @Override
    public Subject updateSubject(SubjectDTO subjectDTO, Integer subjectId) {
        return subjectRepository.findById(subjectId)
                .map(oldSubject -> {
                    oldSubject.setCode(subjectDTO.getCode());
                    oldSubject.setName(subjectDTO.getName());

                    return subjectRepository.save(oldSubject);
                })
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(subjectId, Subject.class);
                });
    }

    @Override
    public Subject getSubjectByClassId(Integer classId) {
        return subjectRepository.findByClassesId(classId);
    }

}
