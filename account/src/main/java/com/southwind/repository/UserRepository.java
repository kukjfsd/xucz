package com.southwind.repository;

import com.southwind.common.aspect.impl.annotation.AspectLog;
import com.southwind.entity.User;
@AspectLog(value = "服务：acount  ",ignore = false)
public interface UserRepository {
    public User login(String username,String password);
}
