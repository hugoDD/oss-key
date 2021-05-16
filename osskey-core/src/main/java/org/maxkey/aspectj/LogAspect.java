package org.maxkey.aspectj;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.maxkey.aspectj.annotation.Log;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.domain.HistoryLogs;
import org.maxkey.domain.UserInfo;
import org.maxkey.log.IOperLog;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired(required = false)
    @Qualifier("historyLogsService")
    private IOperLog historyLogsService;

    // 配置织入点
    @Pointcut("@annotation(org.maxkey.aspectj.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLogErr(joinPoint, e);
    }

    protected void handleLogErr(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            Log message = getAnnotationLog(joinPoint);
            if (message == null) {
                return;
            }

            // 获取当前的用户
            UserInfo userInfo = WebContext.getUserInfo();
            //LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

            String msg = ConstantsOperateMessage.getOperateError(message.operateType());

            //创建日志记录
            HistoryLogs logs = new HistoryLogs(
                    message.title(),
                    message.code(),
                    msg,
                    e.getMessage(),
                    MessageType.error.toString(),
                    message.operateType().toString(),
                    userInfo == null ? null : userInfo.getId(),
                    userInfo == null ? null : userInfo.getUsername(),
                    ""
            );
            log.debug("insert db logs content : " + logs);
            historyLogsService.save(logs);//日志插入数据库


        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());

        }
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log message = getAnnotationLog(joinPoint);
            if (message == null) {
                return;
            }

            // 获取当前的用户
            UserInfo userInfo = WebContext.getUserInfo();
            //LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

            // *========数据库日志=========*//
            String params = "";
            if (message.isSaveRequestData()) {
                // 获取参数的信息，传入到数据库中。
                params = argsArrayToString(joinPoint.getArgs());
            }

            //创建日志记录
            HistoryLogs logs = new HistoryLogs(
                    message.title(),
                    message.code(),
                    ConstantsOperateMessage.getOperateSuccess(message.operateType()),
                    params,
                    message.messageType().toString(),
                    message.operateType().toString(),
                    userInfo == null ? null : userInfo.getId(),
                    userInfo == null ? null : userInfo.getUsername(),
                    ""
            );
            log.debug("insert db logs content : " + logs);
            historyLogsService.save(logs);//日志插入数据库


        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());

        }
    }



    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
