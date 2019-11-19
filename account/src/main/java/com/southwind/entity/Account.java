package com.southwind.entity;

import com.southwind.common.aspect.impl.annotation.AspectLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AspectLog(value = "服务：acount  ",ignore = false)
public class Account {
    private long id;
    private String username;
    private String password;
    private String nickname;
    private String gender;
    private String telephone;
    private Date registerdate;
    private String address;
}
