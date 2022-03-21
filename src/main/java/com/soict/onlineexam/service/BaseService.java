package com.ngovangiang.onlineexam.service;

import java.util.List;

public interface BaseService<T, ID> {

    List<T> getAll();

    T getById(ID id);

    void deleteById(ID id);
}
