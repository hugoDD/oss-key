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
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


/**
 * .
 *
 * @author Crystal.Sea
 */
@Data
@AllArgsConstructor
@TableName("MXK_HISTORY_LOGS")
public class HistoryLogs implements Serializable {
    private static final long serialVersionUID = 6560201093784960493L;
    @TableId
    private String id;
    private String serviceName;
    private String message;
    private String content;
    private String messageType;
    private String operateType;
    private String username;
    private String code;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private String startDate;
    private String endDate;


    public HistoryLogs() {
    }

    /**
     * HistoryLogs.
     * @param serviceName String
     * @param code String
     * @param message String
     * @param content String
     * @param messageType String
     * @param operateType String
     * @param createdBy String
     * @param username String
     * @param cname String
     */
    public HistoryLogs(String serviceName, String code,
                       String message, String content,
                       String messageType,String operateType,
                       String createdBy, String username, String cname) {
        this.serviceName = serviceName;
        this.code = code;
        this.message = message;
        this.content = content;
        this.messageType = messageType;
        this.operateType = operateType;
        this.createdBy = createdBy;
        this.username = username;

    }
}
