package org.maxkey.aspectj.annotation;

import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.MessageType;
import org.maxkey.web.message.OperateType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义操作日志记录注解
 *
 * @author ruoyi
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块
     * 服务名称
     */
    public String title() default "";

    // 信息内容
    public String message() default "";
    // 信息代码
    public String code() default "";

    // 类型
    public MessageType messageType() default MessageType.info;
    // 操作类型
    public OperateType operateType() default OperateType.unknown;
    // 范围
    public  MessageScope messageScope() default MessageScope.JSON;
    //是否要保存请求的数据
    public boolean isSaveRequestData() default false;
}
