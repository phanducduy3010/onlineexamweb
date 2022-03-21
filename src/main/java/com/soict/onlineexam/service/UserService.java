package com.ngovangiang.onlineexam.service;

import com.ngovangiang.onlineexam.entity.User;

public interface UserService {

    User checkUser(String username, String password);
}
