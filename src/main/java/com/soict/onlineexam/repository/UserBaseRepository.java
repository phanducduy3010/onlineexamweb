package com.ngovangiang.onlineexam.repository;

import com.ngovangiang.onlineexam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends JpaRepository<T, Integer> {

    Optional<T> findByEmail(String email);

    List<T> findByEmailContainsIgnoreCase(String partOfEmail);

    Optional<T> findByName(String name);

    List<T> findByNameContainsIgnoreCase(String partOfName);

    List<T> findByEmailContainsAndNameContainsAllIgnoreCase(String partOfEmail, String partOfName);
}
