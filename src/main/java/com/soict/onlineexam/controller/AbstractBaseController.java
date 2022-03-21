package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.service.BaseService;

import java.util.List;

public class AbstractBaseController<T, ID extends Number> implements BaseController<T, ID>{

    private BaseService<T, ID> baseService;

    protected AbstractBaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
    }

    @Override
    public List<T> getAll() {
        return baseService.getAll();
    }

    @Override
    public T getOne(ID id) {
        return baseService.getById(id);
    }

    @Override
    public void deleteOne(ID id) {
        baseService.deleteById(id);
    }
}
