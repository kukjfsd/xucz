package com.southwind.repository;

import com.southwind.common.aspect.impl.annotation.AspectLog;
import com.southwind.entity.Admin;

@AspectLog(value = "服务：acount  ",ignore = false)
public interface AdminRepository {
    public Admin login(String username,String password);
}
