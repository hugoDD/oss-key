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


package org.maxkey.constants;

import org.maxkey.web.WebContext;
import org.maxkey.web.message.OperateType;

public final class ConstantsOperateMessage {

    public static final String INSERT_SUCCESS = "message.action.insert.success";
    public static final String INSERT_ERROR = "message.action.insert.error";

    public static final String UPDATE_SUCCESS = "message.action.update.success";
    public static final String UPDATE_ERROR = "message.action.update.error";

    public static final String DELETE_SUCCESS = "message.action.delete.success";
    public static final String DELETE_ERROR = "message.action.delete.error";

    public static String getOperateSuccess(OperateType operateType){
        switch (operateType){
            case add:
                return WebContext.getI18nValue(INSERT_SUCCESS);
            case update:
                return WebContext.getI18nValue(UPDATE_SUCCESS);
            case delete:
                return WebContext.getI18nValue(DELETE_SUCCESS);
        }
        return operateType.name();
    }

    public static String getOperateError(OperateType operateType){
        switch (operateType){
            case add:
                return WebContext.getI18nValue(INSERT_ERROR);
            case update:
                return WebContext.getI18nValue(UPDATE_ERROR);
            case delete:
                return WebContext.getI18nValue(DELETE_ERROR);
        }
        return operateType.name();
    }

}
