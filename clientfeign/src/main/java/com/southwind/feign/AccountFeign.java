package com.southwind.feign;

import com.southwind.common.aspect.impl.annotation.AspectLog;
import com.southwind.entity.Account;
import com.southwind.hystrix.AccountFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//fallback 熔断类
@FeignClient(value = "account", fallback = AccountFeignHystrix.class)
@AspectLog(value = "服务：clientfeign  ",ignore = false)
public interface AccountFeign {

    @GetMapping("/account/login/{username}/{password}/{type}")
    public Account login(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("type") String type);
}
