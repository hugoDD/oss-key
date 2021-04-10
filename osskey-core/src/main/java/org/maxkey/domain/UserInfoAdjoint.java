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


@Data
@TableName("MXK_USERINFO_ADJUNCT")
public class UserInfoAdjoint implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8634054312223379561L;
    protected String displayName;
    protected String userId;
    protected String workCountry;

    // for work
    protected String workRegion;// province;
    protected String workLocality;// city;
    protected String workStreetAddress;
    protected String workAddressFormatted;
    protected String workEmail;
    protected String workPhoneNumber;
    protected String workPostalCode;
    protected String workFax;
    protected String costCenter;
    protected String organization;
    protected String division;
    protected String departmentId;
    protected String department;
    protected String jobTitle;
    protected String jobLevel;
    protected String managerId;
    protected String manager;
    protected String assistantId;
    protected String assistant;
    protected String entryDate;
    protected String quitDate;
    @TableId
    String id;


}
