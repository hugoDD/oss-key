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


import java.util.List;
import java.util.Map;


/**
 * @author hugoDD
 *
 */
public  interface ReportMapper  {

	 Integer analysisDay(String reportParameter);
	 Integer analysisNewUsers(String reportParameter);
	 Integer analysisOnlineUsers(String reportParameter);
	 Integer analysisActiveUsers(String reportParameter);

	 List<Map<String,Object>> analysisDayHour(String reportParameter);

	 List<Map<String,Object>> analysisMonth(String reportParameter);

	 List<Map<String,Object>> analysisBrowser(Map<String, Object> reportParameter);

	 List<Map<String,Object>> analysisApp(Map<String, Object> reportParameter);


}
