package com.ngovangiang.onlineexam.repository;

import com.ngovangiang.onlineexam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("select q from " +
            "Question q join fetch q.topic t join t.subject s " +
            "where t.name like concat('%', :topicName, '%') " +
            "and s.name like concat('%', :subjectName, '%') " +
            "and s.code like concat('%', :subjectCode, '%')")
    List<Question> findBySubjectAndTopicInfo(@Param("topicName") String topicName,
                                             @Param("subjectCode") String subjectCode,
                                             @Param("subjectName") String subjectName);
}
