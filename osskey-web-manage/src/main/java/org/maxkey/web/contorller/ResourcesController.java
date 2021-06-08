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


package org.maxkey.web.contorller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.domain.PageResults;
import org.maxkey.domain.Resources;
import org.maxkey.domain.param.PageSearchFilter;
import org.maxkey.domain.result.PageResult;
import org.maxkey.persistence.service.ResourcesService;
import org.maxkey.web.WebContext;
import org.maxkey.web.component.TreeNode;
import org.maxkey.web.component.TreeNodeList;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value={"/resources"})
public class ResourcesController {
	final static Logger _logger = LoggerFactory.getLogger(ResourcesController.class);

	@Autowired
	@Qualifier("resourcesService")
	ResourcesService resourcesService;



	@RequestMapping(value={"/list"})
	public ModelAndView resourcesList(){
		return new ModelAndView("resources/resourcesList");
	}

	@RequestMapping(value={"/selectResourcesList"})
	public ModelAndView selectResourcesList(){
		return new ModelAndView("resources/selectResourcesList");
	}


	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public PageResult<Resources> queryDataGrid(PageSearchFilter search, @ModelAttribute("resources") Resources resources) {
		_logger.debug(""+resources);
		IPage<Resources> page =  resourcesService.page(search.newPage(),search.rqslToQuery(Resources.class));
		return PageResult.newInstance(page.getTotal(),page.getRecords());
	}


	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("resources/resourceAdd");
	}

	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("resources/resourceUpdate");
		Resources resource=resourcesService.getById(id);
		modelAndView.addObject("model",resource);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("resource") Resources resource) {
		_logger.debug("-Add  :" + resource);

		if (resourcesService.save(resource)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);

		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}

	}

	/**
	 * 查询
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"})
	public Message query(@ModelAttribute("resource") Resources resource) {
		_logger.debug("-query  :" + resource);
		if (resourcesService.getById(resource)!=null) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);

		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
		}

	}

	/**
	 * 修改
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})
	public Message update(@ModelAttribute("resource") Resources resource) {
		_logger.debug("-update  resource :" + resource);

		if (resourcesService.updateById(resource)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);

		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}

	}


	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("resource") Resources resource) {
		_logger.debug("-delete  resource :" + resource);

		if (resourcesService.removeById(resource.getId())) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);

		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}

	}

	  @ResponseBody
	  @RequestMapping({"/tree"})
	  public List<HashMap<String, Object>> resourcesTree(
	          @RequestParam(value = "appId", required = false) String appId,
	          @RequestParam(value = "appName", required = false) String appName
	          ) {
	    _logger.debug("resourcesTree appId :" + appId + " ,appName " + appName);
	    Resources queryRes = new Resources();
	    queryRes.setAppId(appId);
	    List<Resources> resourcesList = this.resourcesService.queryResourcesTree(queryRes);
	    TreeNodeList treeNodeList = new TreeNodeList();

	    TreeNode rootNode = new TreeNode(appId, appName);
	    rootNode.setAttr("open", Boolean.valueOf(true));
	    treeNodeList.addTreeNode(rootNode.getAttr());

	    for (Resources res : resourcesList) {
	      TreeNode treeNode = new TreeNode(res.getId(), res.getName());
	      treeNode.setAttr("data", res);
	      treeNode.setPId(res.getParentId());
	      treeNodeList.addTreeNode(treeNode.getAttr());
	    }


	    return treeNodeList.getTreeNodeList();
	  }
}