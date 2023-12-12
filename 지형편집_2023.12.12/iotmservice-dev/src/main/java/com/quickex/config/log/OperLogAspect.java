package com.quickex.config.log;

import com.alibaba.fastjson.JSONObject;
import com.quickex.core.result.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Log processing
 */
@Aspect
@Component
@Slf4j
public class OperLogAspect {

    /**
     * Set the operation log entry point to record the entry code of the operation log at the annotation position
     */
    @Pointcut("@annotation(com.quickex.config.log.OperLog)")
    public void operLogPoinCut() { }


    /**
     * Record interface access
     *
     * @param joinPoint breakthrough point
     * @param r         Return results
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "r")
    public void saveOperLog(JoinPoint joinPoint, R r) {
        try {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            JSONObject logModel = getNameAndValue(joinPoint);
            // Get the method where the pointcut is located
            Method method = signature.getMethod();
            // Get the requested class name + method name
            String methodName = joinPoint.getTarget().getClass().getName() + "." +method.getName();

            // Get operation
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                OperType operType = opLog.operType();
                String requestUrl = opLog.operDesc();
                String resultCode = r.get("code").toString();

                String infoMsg = "request record ["+requestUrl+"][" + methodName + "][" + operType + "][" + logModel + "] res [" + resultCode + "]";
                log.info(infoMsg);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("write info error：", ex);
        }
    }

    /**
     * Set the operation exception pointcut and record the exception log
     */
    @Pointcut("execution(* com.quickex.web..*(..)) || execution(* com.quickex.service..*(..))")
    public void operExceptionLogPoinCut() { }

    /**
     * Record exception
     *
     * @param joinPoint breakthrough point
     * @param ex        Abnormal information
     */
    @ResponseBody
    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "ex")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable ex) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            JSONObject logModel = getNameAndValue(joinPoint);

            // Get the method where the pointcut is located
            Method method = signature.getMethod();

            // Get the requested class name + method name
            String methodName = joinPoint.getTarget().getClass().getName()  + "." + method.getName();

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw, true));

            ex.printStackTrace();
            String logMsg = "request error [" + methodName + "][" + logModel + "] msg start =====>> [" + sw.toString() + "]<<===== msg end";
            log.error(logMsg);

        } catch (Exception e2) {
            e2.printStackTrace();
            log.error("write error error：", e2);
        }

    }

    /**
     * Get input parameters
     * @param joinPoint
     * @return
     */
    private JSONObject getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {

            //File parameters are not processed
            if(!paramNames[i].equals("file")&&!paramNames[i].equals("files")){
                param.put(paramNames[i], paramValues[i]);
            }

        }
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(param));
        return jsonObject;
    }

}
