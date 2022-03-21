package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.dto.request.SubjectDTO;
import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Subject;
import com.ngovangiang.onlineexam.entity.Topic;

import java.util.List;

public interface SubjectService extends BaseService<Subject, Integer> {

    List<Subject> searchSubjectsByCode(String partOfCode);

    List<Class> getClassesBySubjectId(Integer id);

    List<Topic> getTopicsBySubjectId(Integer id);

    Subject createSubject(SubjectDTO subjectDTO);

    Subject updateSubject(SubjectDTO subjectDTO, Integer id);

    Subject getSubjectByClassId(Integer classId);
}
