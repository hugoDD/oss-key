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
import org.maxkey.domain.apps.AppsSAML20Details;
import org.maxkey.persistence.mapper.AppsSaml20DetailsMapper;
import org.springframework.stereotype.Service;

@Service
public class AppsSaml20DetailsService  extends ServiceImpl<AppsSaml20DetailsMapper,AppsSAML20Details> {


	public  AppsSAML20Details  getAppDetails(String id){
		return  baseMapper.getAppDetails(id);
	}
}