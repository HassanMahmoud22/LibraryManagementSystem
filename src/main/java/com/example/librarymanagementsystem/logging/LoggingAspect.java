package com.example.librarymanagementsystem.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.librarymanagementsystem.service.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Method '").append(methodName).append("' in class '").append(className).append("' is called. ");

        if (args.length > 0) {
            messageBuilder.append("Arguments: [");
            for (Object arg : args) {
                messageBuilder.append(arg.toString()).append(", ");
            }
            messageBuilder.delete(messageBuilder.length() - 2, messageBuilder.length());
            messageBuilder.append("]");
        }
        logger.info(messageBuilder.toString());
    }

    @AfterReturning(pointcut = "execution(* com.example.librarymanagementsystem.service.*.*(..))", returning = "result")
    public void logCacheStatus(JoinPoint joinPoint, Object result) {
        if (result != null && isCacheableMethod(joinPoint)) {
            logger.info("Value retrieved from cache.");
        } else {
            logger.info("Value retrieved from database.");
        }
    }

    private boolean isCacheableMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.isAnnotationPresent(Cacheable.class);
    }

    @AfterThrowing(pointcut = "execution(* com.example.librarymanagementsystem.service.*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception in method '{}': {}", methodName, exception.getMessage());
    }

    @Around ("execution(* com.example.librarymanagementsystem.service.*.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method '{}' executed in {} ms", methodName, executionTime);
        return result;
    }
}
