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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.maxkey.domain.RoleMember;
import org.maxkey.domain.Roles;
import org.maxkey.persistence.mapper.RoleMemberMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleMemberService  extends ServiceImpl<RoleMemberMapper,RoleMember> {



    public int addDynamicRoleMember(Roles dynamicRole) {
        return baseMapper.addDynamicRoleMember(dynamicRole);
    }

    public int deleteDynamicRoleMember(Roles dynamicRole) {
        return baseMapper.deleteDynamicRoleMember(dynamicRole);
    }

    public int deleteByRoleId(String roleId) {
		UpdateWrapper<RoleMember> updateWrapper = new UpdateWrapper<>();
		updateWrapper.lambda().eq(RoleMember::getRoleId,roleId);
		return baseMapper.delete(updateWrapper);
    }
}
