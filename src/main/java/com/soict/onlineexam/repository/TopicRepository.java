package com.ngovangiang.onlineexam.repository;

import com.ngovangiang.onlineexam.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    Optional<Topic> findByName(String name);

    List<Topic> findByNameContainsIgnoreCase(String partOfName);
}
