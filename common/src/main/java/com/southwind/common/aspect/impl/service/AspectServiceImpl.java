package com.southwind.common.aspect.impl.service;

import com.southwind.common.aspect.impl.AspectBean;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("aspectService")
public class AspectServiceImpl implements AspectService {

    @Override
    public AspectBean testAspect(AspectBean aspectBean) throws InterruptedException {
        // sleep 1 second
        Thread.sleep(1000);
        aspectBean.setName("update name");
        aspectBean.setAge(20);
        aspectBean.setSex(0);
        aspectBean.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
        return aspectBean;
    }

    @Override
    public boolean init() {
        return false;
    }
}
