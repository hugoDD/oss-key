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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
   ID                   varchar(40)                    not null,
   APPROLEID            varchar(40)                    null,
   UID	                varchar(40)	                   null
   constraint PK_ROLES primary key clustered (ID)
 */
@Data
@TableName("MXK_ROLE_MEMBER")
public class RoleMember extends UserInfo implements Serializable {
    private static final long serialVersionUID = -8059639972590554760L;
    @TableId
    String id;

    private String roleId;
    private String roleName;

    private String memberId;
    private String memberName;

    private String type;// User or Roles


    public RoleMember(String groupId, String roleName, String arrMemberId, String arrMemberName, String user) {
        this.roleId = groupId;
        this.roleName = roleName;
        this.memberId = arrMemberId;
        this.memberName = arrMemberName;
        this.userType = user;

    }
}
