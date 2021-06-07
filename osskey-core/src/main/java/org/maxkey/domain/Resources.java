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
@TableName("MXK_RESOURCES")
public class Resources implements Serializable {
    private static final long serialVersionUID = 2567171742999638608L;
    @TableId
    private String id;

    private String name;

    private int sortIndex;

    private String appId;
    @TableField(exist = false)
    private String appName;

    private String parentId;

    private String parentName;

    private String resourceType;

    private String resourceIcon;

    private String resourceStyle;

    private String resourceUrl;

    private String resourceAction;

    private String status;

    private String description;

    private String createdBy;

    private String createdDate;

    private String modifiedBy;

    private String modifiedDate;


}
