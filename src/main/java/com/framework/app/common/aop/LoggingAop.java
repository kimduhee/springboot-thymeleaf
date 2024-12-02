package com.framework.app.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.app.common.handler.CommonApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
@Profile(value={"local","dev"})
public class LoggingAop {

    @Pointcut("execution(* com.framework.app.controller..*Controller.*(..))")
    private void controllerCut(){};

    @Around("controllerCut()")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();

        try {
            stopWatch.start();
            return joinPoint.proceed(); // 타겟 호출
        } finally {
            stopWatch.stop();
            if (log.isInfoEnabled()) {
                log.info("******************************************************");
                log.info("* execution time");
                log.info("* - method : {}", joinPoint.getSignature().toShortString());
                log.info("* - Total execution time : {}", stopWatch.getTotalTimeSeconds());
                log.info("******************************************************");
            }
        }
    }

    @Before("controllerCut()")
    public void beforeParameterLog(JoinPoint joinPoint) {
        try {
            //Method method = getMethod(joinPoint);
            ObjectMapper objectMapper = new ObjectMapper();

            if(log.isInfoEnabled()) {
                log.info("******************************************************");
                log.info("* Controller Start!");
                log.info("* - Method name : {}", getMethod(joinPoint));
            }

            Object[] args = joinPoint.getArgs();

            if(args.length <= 0) {
                if(log.isInfoEnabled()) {
                    log.info("* - No parameter");
                }
            } else {
                for(Object arg : args) {
                    if(log.isInfoEnabled()) {
                        log.info("* - Parameter type : {}", arg.getClass().getSimpleName());
                        log.info("* - Parameter value :");
                        log.info("* {}", objectMapper.writeValueAsString(arg));
                    }
                }
            }
        } catch(Exception e) {
            log.info("*");
        } finally {
            if(log.isInfoEnabled()) {
                log.info("******************************************************");
            }
        }
    }

    @AfterReturning(value="controllerCut()", returning="returnObj")
    private void afterReturnLog(JoinPoint joinPoint, Object returnObj) {

        try {
            if (log.isInfoEnabled()) {
                //Method method = getMethod(joinPoint);
                log.info("******************************************************");
                log.info("* Controller End!");
                log.info("* - Method name : {}", getMethod(joinPoint));
                if(returnObj instanceof String) {
                    log.info("* - Return value : {}.html", returnObj);
                } else if(returnObj instanceof ResponseEntity) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Object obj = ((CommonApiResponse) ((ResponseEntity) returnObj).getBody()).getData();
                    log.info("* - Return value : {}", objectMapper.writeValueAsString(obj));
                }
                log.info("******************************************************");
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    private String getMethod(JoinPoint joinpoint) {
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        return joinpoint.getSignature().getDeclaringTypeName()+"."+signature.getMethod().getName();
    }
}
