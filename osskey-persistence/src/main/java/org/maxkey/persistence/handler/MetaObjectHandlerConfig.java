//package org.maxkey.persistence.handler;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Slf4j
//@Component
//public class MetaObjectHandlerConfig implements MetaObjectHandler {
//
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        log.info("insertFill");
//        setFieldValByName("createdDate", new Date(), metaObject);
//        setFieldValByName("lastUpdatedDate", new Date(), metaObject);
//
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        log.info("updateFill");
//        this.setFieldValByName("lastUpdatedDate", new Date(), metaObject);
//
//    }
//}
//
