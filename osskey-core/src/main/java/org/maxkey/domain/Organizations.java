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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("MXK_ORGANIZATIONS")
public class Organizations implements ITree, Serializable {

    private static final long serialVersionUID = 5085413816404119803L;

    @TableId
    private String id;
    private String code;
    private String name;
    private String fullName;
    private String parentId;
    private String parentName;
    private String type;
    private String codePath;
    private String namePath;
    private String level;
    private String hasChild;
    private String division;
    private String country;
    private String region;
    private String locality;
    private String street;
    private String address;
    private String contact;
    private String postalCode;
    private String phone;
    private String fax;
    private String email;
    private String sortIndex;
    private String description;

    @TableField(exist = false)
    private String sortOrder;

    private String status;


    @Override
    public boolean isOpen() {
        return this.getHasChild() != null && this.getHasChild().startsWith("Y");
    }

    @Override
    public String getPId() {
        return this.parentId;
    }
}
