package org.maxkey.domain.param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.maxkey.rsql.RsqlToMybatisPlusWrapper;

import java.io.Serializable;

@Data
public class SearchFilter implements Serializable {


    private String rsqlFilter;

    private String[] ids;


    public <T> QueryWrapper<T> rqslToQuery(Class<T> clazz){
       return  (QueryWrapper)RsqlToMybatisPlusWrapper.getInstance().rsqlToWrapper(rsqlFilter,clazz);
    }

}
