package org.maxkey.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class TokenVO {
    private String accessToken;
    private List<String> roles;
    private String name;
    private String avatar;
    private String introduction;
    private String email;
    private String phone;

}
