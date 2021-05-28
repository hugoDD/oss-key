package org.maxkey.mgt.contorller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.maxkey.aspectj.annotation.Log;
import org.maxkey.constants.ConstantsCode;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.domain.ExcelImport;
import org.maxkey.domain.ITree;
import org.maxkey.domain.Organizations;
import org.maxkey.domain.param.PageSearchFilter;
import org.maxkey.domain.param.SearchFilter;
import org.maxkey.domain.result.PageResult;
import org.maxkey.domain.result.ResponseResult;
import org.maxkey.persistence.service.OrganizationsService;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.MessageType;
import org.maxkey.web.message.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping({"/auth/manage/orgs"})
public class OrgsController {
    static final Logger _logger = LoggerFactory.getLogger(OrgsController.class);


    @Autowired
    private OrganizationsService organizationsService;


    @GetMapping({"/tree"})
    public ResponseResult<List<? extends ITree>> organizationsTree(SearchFilter searchFilter) {
        List<Organizations> organizationsList = this.organizationsService.list(searchFilter.rqslToQuery(Organizations.class));
        List<? extends ITree> trees = organizationsList;

        return ResponseResult.newInstance(trees);
    }


    @PostMapping(value = {"/pageresults"})
    public PageResult<Organizations> pageResults(@RequestBody PageSearchFilter page) {
        IPage<Organizations> rs = organizationsService.page(page.newPage(), page.rqslToQuery(Organizations.class));
        return PageResult.newInstance(rs.getTotal(), rs.getRecords());

    }

    @Log(title = "orgs:insert", operateType = OperateType.add, messageScope = MessageScope.DB)
    @PostMapping({"/add"})
    public ResponseResult<String> insert(@RequestBody Organizations org) {
        _logger.debug("-Add  : {}", org);
        //if (null == org.getId() || org.getId().equals("")) {
        //org.generateId();
        //}

        if (this.organizationsService.insert(org)) {
            return ResponseResult.newInstance(WebContext.getI18nValue("message.action.insert.success"));
        }
        String errorMessage = WebContext.getI18nValue("message.action.insert.success");
        return ResponseResult.newInstance(ConstantsCode.update_fail, errorMessage);
    }


    @GetMapping({"/query/{id}"})
    public ResponseResult<String> query(@PathVariable("id") String id) {
        _logger.debug("-query  :{}" + id);
        if (this.organizationsService.getById(id) != null) {
            return ResponseResult.newI18nInstance("message.action.insert.success");
        }
        return ResponseResult.newInstance(ConstantsCode.update_fail, "message.action.insert.error");
    }


    @Log(title = "orgs:update", operateType = OperateType.update, messageScope = MessageScope.DB)
    @PutMapping({"/update"})
    public ResponseResult<String> update(@RequestBody Organizations org) {
        _logger.debug("-update  organization :" + org);
        if (this.organizationsService.update(org)) {
            return ResponseResult.newI18nInstance("message.action.update.success");
        }
        return ResponseResult.newInstance(ConstantsCode.update_fail, "message.action.update.error");
    }

    @Log(title = "orgs:delete", operateType = OperateType.delete, messageScope = MessageScope.DB)
    @DeleteMapping({"/delete/{ids}"})
    public ResponseResult<String> delete(@PathVariable("ids") String[] ids) {
        _logger.debug("-delete  organization ids : {}" , ids);
        if (this.organizationsService.removeByIds(Arrays.asList(ids))) {
            return ResponseResult.newI18nInstance("message.action.delete.success");
        }
        return ResponseResult.newInstance(ConstantsCode.update_fail, "message.action.delete.success");
    }


    @PostMapping(value = "/import")
    public ResponseResult<String> importing( ExcelImport excelImportFile) {
        if (excelImportFile.getExcelFile() != null && !excelImportFile.getExcelFile().isEmpty() && organizationsService.importing(excelImportFile.getExcelFile())) {
            return ResponseResult.newI18nInstance(ConstantsOperateMessage.INSERT_SUCCESS);
           // new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS), null, MessageType.success, OperateType.add, MessageScope.DB);
        } else {
            return ResponseResult.newInstance(ConstantsCode.update_fail, ConstantsOperateMessage.INSERT_ERROR);
          //  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR), MessageType.error);
        }
    }


}
