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

/**
 * Aspect for logging method calls, cache status, exceptions, and method execution time.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs method call before execution.
     */
    @Before("execution(* com.example.librarymanagementsystem.service.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        // Extract method name, class name, and arguments
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        // Build log message
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
        // Log method call
        logger.info(messageBuilder.toString());
    }

    /**
     * Logs cache status after method execution.
     */
    @AfterReturning(pointcut = "execution(* com.example.librarymanagementsystem.service.*.*(..))", returning = "result")
    public void logCacheStatus(JoinPoint joinPoint, Object result) {
        if (result != null && isCacheableMethod(joinPoint)) {
            logger.info("Value retrieved from cache.");
        } else {
            logger.info("Value retrieved from database.");
        }
    }

    /**
     * Checks if a method is annotated with @Cacheable.
     */
    private boolean isCacheableMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.isAnnotationPresent(Cacheable.class);
    }

    /**
     * Logs exceptions thrown during method execution.
     */
    @AfterThrowing(pointcut = "execution(* com.example.librarymanagementsystem.service.*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception in method '{}': {}", methodName, exception.getMessage());
    }

    /**
     * Logs method execution time.
     */
    @Around("execution(* com.example.librarymanagementsystem.service.*.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // Measure method execution time
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // Log method execution time
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method '{}' executed in {} ms", methodName, executionTime);
        return result;
    }
}