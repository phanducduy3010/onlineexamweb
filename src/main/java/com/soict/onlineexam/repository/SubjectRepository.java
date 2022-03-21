package com.ngovangiang.onlineexam.repository;

import com.ngovangiang.onlineexam.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Optional<Subject> findByCode(String code);

    Optional<Subject> findByName(String name);

    Subject findByClassesId(Integer classId);

    List<Subject> findByCodeContainsIgnoreCase(String partOfCode);

    List<Subject> findByNameContainsIgnoreCase(String partOfName);

    List<Subject> findByNameContainsAndCodeContainsAllIgnoreCase(String partOfName, String partOfCode);
}
