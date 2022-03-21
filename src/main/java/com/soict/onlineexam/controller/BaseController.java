package com.ngovangiang.onlineexam.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BaseController<T, ID extends Number> {

    @GetMapping
    List<T> getAll();

    @GetMapping("/{id}")
    T getOne(@PathVariable @NotNull @Min(1) ID id);

    @DeleteMapping("/{id}")
    void deleteOne(@PathVariable @NotNull @Min(1) ID id);
}
