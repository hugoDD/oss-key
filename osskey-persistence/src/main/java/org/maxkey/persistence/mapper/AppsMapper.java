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


/**
 *
 */
package org.maxkey.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.UserApps;

import java.util.List;

/**
 * @author Crystal.sea
 *
 */
public  interface AppsMapper extends BaseMapper<Apps> {

	 int insertApp(Apps app);

	 int updateApp(Apps app);


	 int updateExtendAttr(Apps app);


     List<UserApps> queryMyApps(UserApps userApplications);
}
