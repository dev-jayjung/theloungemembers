package com.theloungemembers.web.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqeust {
    private String workerId;

    private String password;
}