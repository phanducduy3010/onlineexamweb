package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.ResultDTO;
import com.ngovangiang.onlineexam.entity.Result;
import com.ngovangiang.onlineexam.service.BaseService;
import com.ngovangiang.onlineexam.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/results")
@Validated
public class ResultController extends AbstractBaseController<Result, Integer> {

    private ResultService resultService;

    @Autowired
    public ResultController(BaseService<Result, Integer> baseService, ResultService resultService) {
        super(baseService);
        this.resultService = resultService;
    }

    @PostMapping
    public Result handleResultSubmission(@RequestBody @Valid ResultDTO resultDTO) {
        return resultService.handleResultSubmission(resultDTO);
    }

}
