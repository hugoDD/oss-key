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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.maxkey.domain.Accounts;
import org.maxkey.persistence.mapper.AccountsMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AccountsService  extends ServiceImpl<AccountsMapper,Accounts> {


    public Accounts load(String userId,String appId) {
        Assert.notNull(userId,"userid不能为空");
        Assert.notNull(appId,"appId不能为空");
        LambdaQueryWrapper<Accounts> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Accounts::getUid,userId).eq(Accounts::getAppId,appId);
        Accounts accounts = baseMapper.selectOne(queryWrapper);
        return accounts;
    }

    public Page<Accounts> queryPageResults(IPage<Object> newPage, Accounts appAccounts) {
       return baseMapper.queryPageResults(newPage,appAccounts);
    }
}
