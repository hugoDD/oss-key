package org.maxkey.domain.param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PageSearchFilter extends  SearchFilter{
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    private String sortField;
    private String sortOrder;


    public <T> IPage<T> newPage(){
        Page<T> page =  new Page<>(this.size,this.current);
        if("asc".equalsIgnoreCase(this.sortOrder)){
            page.addOrder( OrderItem.asc(this.sortField));
        }else {
            page.addOrder( OrderItem.desc(this.sortField));
        }
        return page;
    }
}
