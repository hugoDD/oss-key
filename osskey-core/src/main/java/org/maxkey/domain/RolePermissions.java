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
import org.maxkey.constants.ConstantsStatus;

import java.io.Serializable;

@Data
@TableName("MXK_ROLE_PERMISSIONS")
public class RolePermissions implements Serializable {
    private static final long serialVersionUID = -8783585691243853899L;

    @TableId
    private String id;

    private String appId;

    private String roleId;

    private String resourceId;

    private int status = ConstantsStatus.ACTIVE;


    public RolePermissions(String appId, String roleId) {
        this.appId = appId;
        this.roleId = roleId;
    }

    public RolePermissions(String appId, String roleId, String resourceId) {
        this(appId,roleId);
        this.resourceId = resourceId;
    }

    public String getUniqueId() {
        return  appId + "_" + roleId + "_" + resourceId;
    }
}
