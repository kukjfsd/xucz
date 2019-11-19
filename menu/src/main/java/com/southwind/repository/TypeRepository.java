package com.southwind.repository;

import com.southwind.common.aspect.impl.annotation.AspectLog;
import com.southwind.entity.Type;

import java.util.List;
@AspectLog(value = "服务：menu  ",ignore = false)
public interface TypeRepository {
    public List<Type> findAll();
}
