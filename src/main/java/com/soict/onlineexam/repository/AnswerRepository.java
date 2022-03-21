package com.ngovangiang.onlineexam.repository;

import com.ngovangiang.onlineexam.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    Integer countByResultsIdAndCorrectIsTrue(Integer resultId);
}
