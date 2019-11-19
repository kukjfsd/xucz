package com.southwind.hystrix;

import com.southwind.common.aspect.impl.annotation.AspectLog;
import com.southwind.entity.Account;
import com.southwind.feign.AccountFeign;
import org.springframework.stereotype.Component;

@Component
@AspectLog(value = "服务：clientfeign  ",ignore = false)
public class AccountFeignHystrix implements AccountFeign {

    @Override
    public Account login(String username, String password, String type) {
        Account ac = new Account();
        ac.setAddress("AccountFeignHystrix");
        ac.setNickname("AccountFeignHystrix");
        return ac;
    }
}
