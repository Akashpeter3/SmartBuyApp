package com.app.shopping.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LoggingAdvice {

    Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut(value = "execution(* com.app.shopping.controller..*(..)) || " +
            "execution(* com.app.shopping.service..*(..))"
    )
    public void pointCutExpression() {
    }

    @Around("pointCutExpression()")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().toString();
        Object[] argumentArray = proceedingJoinPoint.getArgs();

        // Mask sensitive information
        Object[] maskedArgs = maskSensitiveInformation(argumentArray);

        logger.info(className + " having method name : " + methodName + "()" + " with arguments : " +
                objectMapper.writeValueAsString(maskedArgs)); // Before advice ends

        // Proceed with the original method call
        Object result = proceedingJoinPoint.proceed();

        logger.info(className + " : " + methodName + "()" + " with return response : " +
                objectMapper.writeValueAsString(result)); // After advice ends
        return result;
    }

    @Around("@annotation(com.app.shopping.advice.TrackExecutionTime)")
    public Object trackMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("method name " + proceedingJoinPoint.getSignature().getName() + "()" + " taken time to execution is " +
                (endTime - startTime) + " ms");
        return object;
    }

    private Object[] maskSensitiveInformation(Object[] args) {
        // Create a new array to avoid modifying the original arguments
        Object[] maskedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String && isSensitive(args[i])) {
                maskedArgs[i] = "*****"; // Mask the sensitive information
            } else {
                maskedArgs[i] = args[i];
            }
        }
        return maskedArgs;
    }

    private boolean isSensitive(Object arg) {
        if (arg instanceof String) {
            String argStr = (String) arg;
            // Check for sensitive keywords in strings directly
            return argStr.toLowerCase().contains("password");
        } else {
            // Use reflection to check fields of custom objects
            Field[] fields = arg.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().toLowerCase().contains("password")) {
                    return true;
                }
            }
        }
        return false;
}}
