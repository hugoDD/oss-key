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

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;

@TableName("MXK_GROUPS")
public class Groups  implements Serializable {

    private static final long serialVersionUID = 4660258495864814777L;

    @TableId
    String id;

    @Length(max = 60)
    String name;

    String dynamic;

    String filters ;

    String orgIdsList;
    String resumeTime;

    String suspendTime;

    int isdefault;
    String description;
    String createdBy;
    String createdDate;
    String modifiedBy;
    String modifiedDate;
    String status;

    public Groups() {
    }

    public Groups(String id) {
        this.id = id;
    }

    /**
     * Groups.
     * @param id String
     * @param name String
     * @param isdefault int
     */
    public Groups(String id, String name, int isdefault) {
        super();
        this.id = id;
        this.name = name;
        this.isdefault = isdefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getOrgIdsList() {
        return orgIdsList;
    }

    public void setOrgIdsList(String orgIdsList) {
        this.orgIdsList = orgIdsList;
    }

    public String getResumeTime() {
        return resumeTime;
    }

    public void setResumeTime(String resumeTime) {
        this.resumeTime = resumeTime;
    }

    public String getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime(String suspendTime) {
        this.suspendTime = suspendTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Groups [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", dynamic=");
        builder.append(dynamic);
        builder.append(", filters=");
        builder.append(filters);
        builder.append(", orgIdsList=");
        builder.append(orgIdsList);
        builder.append(", resumeTime=");
        builder.append(resumeTime);
        builder.append(", suspendTime=");
        builder.append(suspendTime);
        builder.append(", isdefault=");
        builder.append(isdefault);
        builder.append(", description=");
        builder.append(description);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", modifiedBy=");
        builder.append(modifiedBy);
        builder.append(", modifiedDate=");
        builder.append(modifiedDate);
        builder.append(", status=");
        builder.append(status);
        builder.append("]");
        return builder.toString();
    }

}
