package org.maxkey.osskey.vo;

import lombok.Data;

@Data
public class ForgotPasswordVO {
    private String userId;
    private String username;
    private String emailMobile;
    private int forgotType;
}
