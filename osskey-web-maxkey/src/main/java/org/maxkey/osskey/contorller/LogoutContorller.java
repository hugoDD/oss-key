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


package org.maxkey.osskey.contorller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketServices;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authz.singlelogout.DefaultSingleLogout;
import org.maxkey.authz.singlelogout.LogoutType;
import org.maxkey.authz.singlelogout.SamlSingleLogout;
import org.maxkey.authz.singlelogout.SingleLogout;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

@Api(tags = "注销")
@RestController("/auth/manage")
public class LogoutContorller {

	private static Logger _logger = LoggerFactory.getLogger(LogoutContorller.class);

	public static final String RE_LOGIN_URL	=	"reLoginUrl";

	@Autowired
	@Qualifier("authenticationRealm")
	AbstractAuthenticationRealm authenticationRealm;

	@Autowired
	ApplicationConfig applicationConfig;

	@Autowired
    @Qualifier("onlineTicketServices")
    protected OnlineTicketServices onlineTicketServices;

	@ApiOperation(value = "单点注销接口", notes = "reLoginUrl跳转地址",httpMethod="GET")
 	@RequestMapping(value={"/oss/logout"})
 	public ResponseResult<Boolean> logout(HttpServletRequest request,HttpServletResponse response,
 					@RequestParam(value=RE_LOGIN_URL,required=false) String reLoginUrl){
		Boolean result = authenticationRealm.logout(response);
		String onlineTicketId = ((SigninPrincipal)WebContext.getAuthentication().getPrincipal()).getOnlineTicket().getTicketId();
		OnlineTicket onlineTicket = onlineTicketServices.get(onlineTicketId);

		Set<Entry<String, Apps>> entrySet = onlineTicket.getAuthorizedApps().entrySet();

		Iterator<Entry<String, Apps>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Entry<String, Apps> mapEntry = iterator.next();
			_logger.debug("App Id : "+ mapEntry.getKey()+ " , " +mapEntry.getValue());
			if( mapEntry.getValue().getLogoutType() == LogoutType.BACK_CHANNEL){
				SingleLogout singleLogout;
				if(mapEntry.getValue().getProtocol().equalsIgnoreCase(ConstantsProtocols.CAS)) {
					singleLogout =new SamlSingleLogout();
				}else {
					singleLogout = new DefaultSingleLogout();
				}
				singleLogout.sendRequest(onlineTicket.getAuthentication(), mapEntry.getValue());
			}
		}
		onlineTicketServices.remove(onlineTicketId);

		//remove ONLINE_TICKET cookie
		WebContext.expiryCookie(WebContext.getResponse(),
				this.applicationConfig.getBaseDomainName(),
				WebConstants.ONLINE_TICKET_NAME,
				UUID.randomUUID().toString());
		request.getSession().invalidate();
		SecurityContextHolder.clearContext();

		return ResponseResult.newInstance(result);
 	}


}
