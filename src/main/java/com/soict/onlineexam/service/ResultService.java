package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.dto.request.ResultDTO;
import com.ngovangiang.onlineexam.entity.Result;

public interface ResultService extends BaseService<Result, Integer>{

    Result handleResultSubmission(ResultDTO resultDTO);
}
