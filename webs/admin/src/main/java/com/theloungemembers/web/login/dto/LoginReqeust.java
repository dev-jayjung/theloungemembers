package com.theloungemembers.web.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqeust {
    private String workerId;

    private String password;
}