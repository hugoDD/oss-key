package org.maxkey.rsql;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.junit.Test;
import org.maxkey.domain.HistoryLogin;
import org.maxkey.domain.HistoryLoginApps;


public class RsqlToMybatisPlusWrapperTest {

    @Test
    public void rsqlToWrapper() {
        String filter = "id==1,uid=gt=xxx,username=re=test";
        Wrapper<HistoryLogin> query = RsqlToMybatisPlusWrapper.getInstance().rsqlToWrapper(filter, HistoryLogin.class);
        System.out.println(query);
        System.out.println(query.getSqlSegment());
    }
    @Test
    public void rsqlToWrapper2() {
        String filter = "appName=re=\"腾讯云\"";
//        String filter = "username==testdemo";
//        String filter = "loginTime=lt=2021-05-01,loginTime=gt=2021-05-22";
        Wrapper<HistoryLoginApps> query = RsqlToMybatisPlusWrapper.getInstance().rsqlToWrapper(filter, HistoryLoginApps.class,false);
        System.out.println(query);
        System.out.println(query.getSqlSegment());
    }
}
