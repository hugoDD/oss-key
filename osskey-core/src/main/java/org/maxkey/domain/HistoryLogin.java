/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.maxkey.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * @author Crystal.Sea
 */
@Data
@TableName("MXK_HISTORY_LOGIN")
public class HistoryLogin implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1321470643357719383L;
    @TableId
    private String id;
    private String sessionId;
    private String uid;
    private String username;
    private String displayName;
    private String loginType;
    private String message;
    private String code;
    private String provider;
    private String sourceIp;
    private String browser;
    private String platform;
    private String application;
    private String loginUrl;
    private String loginTime;
    private String logoutTime;

    private String startDate;
    private String endDate;


}
