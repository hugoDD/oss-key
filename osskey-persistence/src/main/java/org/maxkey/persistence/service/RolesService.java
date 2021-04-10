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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.maxkey.domain.RolePermissions;
import org.maxkey.domain.Roles;
import org.maxkey.persistence.mapper.RolesMapper;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolesService  extends ServiceImpl<RolesMapper,Roles> {
    final static Logger _logger = LoggerFactory.getLogger(RolesService.class);

    @Autowired
    @Qualifier("roleMemberService")
    RoleMemberService roleMemberService;


	public boolean insertRolePermissions(List<RolePermissions> rolePermissionsList) {
	    return baseMapper.insertRolePermissions(rolePermissionsList)>0;
	};

	public boolean logisticDeleteRolePermissions(List<RolePermissions> rolePermissionsList) {
	     return baseMapper.logisticDeleteRolePermissions(rolePermissionsList)>=0;
	 }

    public List<RolePermissions> queryRolePermissions(RolePermissions rolePermissions){
        return baseMapper.queryRolePermissions(rolePermissions);
    }

    public List<Roles> queryDynamicRoles(Roles dynamicRole){
        return baseMapper.queryDynamicRoles(dynamicRole);
    }

    @Transactional
    public boolean deleteById(String roleId) {
        baseMapper.deleteById(roleId);
        roleMemberService.deleteByRoleId(roleId);
        return true;
    }

    public void refreshDynamicRoles(Roles dynamicRole){
        if(dynamicRole.getDynamic().equals("1")) {
            if(dynamicRole.getOrgIdsList()!=null && !dynamicRole.getOrgIdsList().equals("")) {
                dynamicRole.setOrgIdsList("'"+dynamicRole.getOrgIdsList().replace(",", "','")+"'");
            }

            String filters = dynamicRole.getFilters();
            if(StringUtils.filtersSQLInjection(filters.toLowerCase())) {
                _logger.info("filters include SQL Injection Attack Risk.");
                return;
            }

            filters = filters.replace("&", " AND ");
            filters = filters.replace("|", " OR ");

            dynamicRole.setFilters(filters);

            roleMemberService.deleteDynamicRoleMember(dynamicRole);
            roleMemberService.addDynamicRoleMember(dynamicRole);
        }
    }
}
