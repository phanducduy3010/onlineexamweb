package com.ngovangiang.onlineexam.repository;

import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Student;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends UserBaseRepository<Student> {

    Optional<Student> findByCode(String code);

    List<Student> findByCodeContains(String partOfCode);

    List<Student> findByCodeContainsAndEmailContainsAndNameContainsAllIgnoreCase(
            String partOfCode,
            String partOfEmail,
            String partOfName
    );

    @Query("select s from Student s join fetch s.learningClasses cls join fetch cls.subject where s.id = :studentId")
    Optional<Student> findByIdWithClasses(Integer studentId);
}