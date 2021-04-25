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


package org.maxkey.persistence.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.UserApps;
import org.maxkey.domain.param.PageSearchFilter;
import org.maxkey.persistence.mapper.AppsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppsService  extends ServiceImpl<AppsMapper,Apps> {




    public List<UserApps> queryMyApps(UserApps userApplications){
        return baseMapper.queryMyApps(userApplications);
    }

    public Page<Apps> queryPageResults(IPage<Apps> page, Apps applications) {
        return baseMapper.queryPageResults(page,applications);
    }

    public boolean updateExtendAttr(Apps application) {
        return baseMapper.updateExtendAttr(application)>0;
    }
}
