package org.maxkey.rsql;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.junit.Test;
import org.maxkey.domain.HistoryLogin;



public class RsqlToMybatisPlusWrapperTest {

    @Test
    public void rsqlToWrapper() {
        String filter = "id==1,uid=gt=xxx,username=re=test";
        Wrapper<HistoryLogin> query = RsqlToMybatisPlusWrapper.getInstance().rsqlToWrapper(filter, HistoryLogin.class);
        System.out.println(query);
        System.out.println(query.getSqlSegment());
    }
}
