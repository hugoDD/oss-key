package org.maxkey.osskey.vo;

import lombok.Data;
import org.maxkey.domain.UserInfo;
import org.maxkey.password.onetimepwd.algorithm.KeyUriFormat;

@Data
public class TimeBasedVO {
    private String id;
    private UserInfo userInfo;
    private KeyUriFormat format;
    private String sharedSecret;
    private String hexSharedSecret;
}
