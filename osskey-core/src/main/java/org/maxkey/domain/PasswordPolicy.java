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
import org.maxkey.constants.ConstantsServiceMessage;
import org.maxkey.exception.PasswordPolicyException;

import javax.validation.constraints.NotNull;

/**
 * @author Crystal.Sea
 */

@Data
@TableName("MXK_PASSWORD_POLICY")
public class PasswordPolicy implements java.io.Serializable {

    private static final long serialVersionUID = -4797776994287829182L;
    @TableId
    private String id;
    /**
     * minimum password lengths
     */
    @NotNull
    private int minLength;
    /**
     * maximum password lengths
     */
    @NotNull
    private int maxLength;
    /**
     * least lowercase letter
     */
    @NotNull

    private int lowerCase;
    /**
     * least uppercase letter
     */
    @NotNull

    private int upperCase;
    /**
     * inclusion of numerical digits
     */
    @NotNull

    private int digits;
    /**
     * inclusion of special characters
     */
    @NotNull

    private int specialChar;
    /**
     * correct password attempts
     */
    @NotNull

    private int attempts;
    /**
     * attempts lock Duration
     */
    @NotNull

    private int duration;
    /**
     * require users to change passwords periodically
     */

    private int expiration;

    /**
     * 0 no 1 yes
     */

    private int username;

    /**
     * not include password list
     */

    private int history;


    private int dictionary;


    private int alphabetical;


    private int numerical;


    private int qwerty;


    private int occurances;

    private int randomPasswordLength;


}
