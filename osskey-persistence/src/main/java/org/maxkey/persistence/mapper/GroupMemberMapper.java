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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.maxkey.domain.GroupMember;
import org.maxkey.domain.Groups;

import java.util.List;

/**
 * @author hugoDD
 *
 */

public  interface GroupMemberMapper extends BaseMapper<GroupMember> {

	public List<GroupMember> allMemberInGroup(GroupMember entity);
	public List<GroupMember> memberInGroup(GroupMember entity);
	public List<GroupMember> memberNotInGroup(GroupMember entity);
	public List<GroupMember> groupMemberInGroup(GroupMember entity);

	public int addDynamicGroupMember(Groups dynamicGroup);

	public int deleteDynamicGroupMember(Groups dynamicGroup);

	public int deleteByGroupId(String groupId);


    Page<GroupMember> queryPageResults(IPage<Object> newPage, GroupMember groupMember);
}
