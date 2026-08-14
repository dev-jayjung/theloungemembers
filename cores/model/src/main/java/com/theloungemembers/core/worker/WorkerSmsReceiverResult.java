package com.theloungemembers.core.worker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerSmsReceiverResult {

    private Long uid;

    private String accountId;

    private String userName;

    private String phoneNum;

    private String emailAddress;

    private String role;

}