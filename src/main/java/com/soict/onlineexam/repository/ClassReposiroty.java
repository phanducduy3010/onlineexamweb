package com.ngovangiang.onlineexam.repository;

import com.ngovangiang.onlineexam.entity.Class;
import com.ngovangiang.onlineexam.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ClassReposiroty extends JpaRepository<Class, Integer> {

    Optional<Class> findByCode(String code);

    List<Class> findByCodeContains(String partOfCode);
}
