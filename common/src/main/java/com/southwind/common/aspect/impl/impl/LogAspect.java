package com.southwind.common.aspect.impl.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.southwind.common.aspect.impl.annotation.AspectLog;
import com.southwind.utils.DateTimeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * AspectLog 注解类实现
 */
@Aspect
@Order(100)
@Component
public class LogAspect {

    public static final Logger log =  LoggerFactory.getLogger(LogAspect.class);
    public static final String dateformat = "yyyy:MM:dd HH:mm:ss";
    public static final String STIRNG_START = "\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
    public static final String STIRNG_END = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    //execution the scan of pakage 切点package
    @Pointcut("execution( * com.southwind..*(..))")
    public void serviceLog(){

    }

    @Around("serviceLog()")
    public Object around(ProceedingJoinPoint joinPoint) { // ProceedingJoinPoint 为JoinPoint 的子类，在父类基础上多了proceed()方法，用于增强切面
        try {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuffer requestLog = new StringBuffer();
        requestLog.append("[ " + DateTimeUtils.getCurTimeMilli() + " ] - 请求信息：")
                .append("URL = {" + request.getRequestURI() + "},\t")
                .append("HTTP_METHOD = {" + request.getMethod() + "},\t")
                .append("IP = {" + request.getRemoteAddr() + "},\t")
                .append("CLASS_METHOD = {" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "},\t");

        if(joinPoint.getArgs().length == 0) {
            requestLog.append("ARGS = {} ");
        } else {
            requestLog.append("ARGS = " + new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(joinPoint.getArgs()[0]) + "");
        }
            log.info(requestLog.toString());
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
        }

        try {
            // 获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //java reflect相关类，通过反射得到注解
            Method method = signature.getMethod();
            Class<?> targetClass = method.getDeclaringClass();

            StringBuffer classAndMethod = new StringBuffer();

            //获取类注解Log
            AspectLog classAnnotation = targetClass.getAnnotation(AspectLog.class);
            //获取方法注解Log
            AspectLog methodAnnotation = method.getAnnotation(AspectLog.class);

            //如果类上Log注解不为空，则执行proceed()
            if (classAnnotation != null) {
                if (classAnnotation.ignore()) {
                    //proceed() 方法执行切面方法，并返回方法返回值
                    return joinPoint.proceed();
                }
                classAndMethod.append(classAnnotation.value()).append("-");
            }

            //如果方法上Log注解不为空，则执行proceed()
            if (methodAnnotation != null) {
                if (methodAnnotation.ignore()) {
                    return joinPoint.proceed();
                }
                classAndMethod.append(methodAnnotation.value());
            }

            //拼凑目标类名和参数名
            String target = targetClass.getName() + "#" + method.getName();
            String params = JSONObject.toJSONStringWithDateFormat(joinPoint.getArgs(), dateformat, SerializerFeature.WriteMapNullValue);

            log.info(STIRNG_START + "[ " + DateTimeUtils.getCurTimeMilli() + " ]" + "{} 开始调用--> {} 参数:{}", classAndMethod.toString(), target, params);

            long start = System.currentTimeMillis();
            //如果类名上和方法上都没有Log注解，则直接执行 proceed()
            Object result = joinPoint.proceed();
            long timeConsuming = System.currentTimeMillis() - start;

            log.info("\n{} 调用结束<-- {} 返回值:{} 耗时:{}ms" + STIRNG_END, classAndMethod.toString(), target, JSONObject.toJSONStringWithDateFormat(result, dateformat, SerializerFeature.WriteMapNullValue), timeConsuming);
            return result;
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
        }
        return null;
    }
}
