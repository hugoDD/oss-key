package org.maxkey.autoconfigure;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MybatisAutoConfiguration {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    /**
     * mapper.xml 热加载
     * @return
     */
//    @Bean
//    public MybatisMapperRefresh mybatisMapperRefresh(){
//        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
//        Resource[] resources = new Resource[0];
//        try {
//            resources = resourceResolver.getResources(mapperLocations);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        MybatisMapperRefresh mybatisMapperRefresh = new MybatisMapperRefresh(resources,sqlSessionFactory,10,5,refreshMapper);
//        return mybatisMapperRefresh;
//
//    }

    /**
     * 逻辑删除
     * @return
     */
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }

}
