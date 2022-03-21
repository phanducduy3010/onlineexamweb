package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractBaseService<T, ID> implements BaseService<T, ID> {

    private final Class<T> typeClass;

    private JpaRepository<T, ID> repository;

    protected AbstractBaseService(JpaRepository<T, ID> repository, Class<T> typeClass) {
        this.repository = repository;
        this.typeClass = typeClass;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T getById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(id, typeClass);
                });
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
