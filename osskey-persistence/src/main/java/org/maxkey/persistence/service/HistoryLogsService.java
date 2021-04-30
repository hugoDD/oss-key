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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.maxkey.domain.HistoryLogs;
import org.maxkey.log.IOperLog;
import org.maxkey.persistence.mapper.HistoryLogsMapper;
import org.springframework.stereotype.Service;

@Service
public class HistoryLogsService  extends ServiceImpl<HistoryLogsMapper,HistoryLogs> implements IOperLog {


    public Page<HistoryLogs> queryPageResults(IPage<HistoryLogs> page, HistoryLogs historyLogs) {

        return baseMapper.queryPageResults(page,historyLogs);
    }
}
