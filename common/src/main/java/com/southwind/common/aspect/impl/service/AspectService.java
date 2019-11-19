package com.southwind.common.aspect.impl.service;

import com.southwind.common.aspect.impl.annotation.AspectLog;
import com.southwind.common.aspect.impl.AspectBean;

//@AspectLog(value = "aspect test",ignore = true)
@AspectLog(value = "测试 aspect test",ignore = false)
public interface AspectService {

//    @AspectLog("this is a method for test aspect.")
    public AspectBean testAspect(AspectBean aspectBean) throws InterruptedException;

    public boolean init();

}
