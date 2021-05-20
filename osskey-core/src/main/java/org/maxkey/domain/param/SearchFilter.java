package org.maxkey.domain.param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.maxkey.rsql.RsqlToMybatisPlusWrapper;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;

import java.io.Serializable;

@Data
public class SearchFilter implements Serializable {

    private String rsqlFilter;

    private String[] ids;


    public <T> QueryWrapper<T> rqslToQuery(Class<T> clazz){
        if(StringUtils.isNullOrBlank(rsqlFilter)&& WebContext.getUserInfo()!=null){
            rsqlFilter = "uid=="+ WebContext.getUserInfo().getId();
        }
       return  (QueryWrapper)RsqlToMybatisPlusWrapper.getInstance().rsqlToWrapper(this.rsqlFilter,clazz,false);
    }

    public <T> QueryWrapper<T> toLineRqslToQuery(Class<T> clazz){
        if(StringUtils.isNullOrBlank(rsqlFilter)&& WebContext.getUserInfo()!=null){
            rsqlFilter = "uid=="+ WebContext.getUserInfo().getId();
        }
        return  (QueryWrapper)RsqlToMybatisPlusWrapper.getInstance().rsqlToWrapper(this.rsqlFilter,clazz);
    }

}
