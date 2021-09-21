package com.chatroom.app.aspect;

import com.chatroom.app.dto.message.MessageDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggerAspect {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Pointcut("execution(* com.chatroom.app.controller.*.save(..))")
    public void save(){
        // Empty function for the sake of pointcut expression
        // It covers all the save methods in the controller package
    }

    @Pointcut("execution(* com.chatroom.app.controller.*.update(..))")
    public void update(){
        // Empty function for the sake of pointcut expression
        // It covers all the update methods in the controller package
    }

    @Pointcut("execution(* com.chatroom.app.controller.*.delete(..))")
    public void delete(){
        // Empty function for the sake of pointcut expression
        // It covers all the delete methods in the controller package
    }

    @Before("execution(* com.chatroom.app.controller.MessageController.sendMessage(..))")
    public void beforeWritingMessages(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg instanceof MessageDTO){
                MessageDTO message = (MessageDTO)arg;
                logger.info("[MESSAGE] : Message is sent : {}" , message);
            }
        }
    }

    @AfterReturning("save()")
    public void logSave(JoinPoint joinPoint){
        logger.info("[SAVED] : Saved a record of {} - Values : {}" , joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getArgs()[0]);
    }

    @AfterReturning("update()")
    public void logUpdate(JoinPoint joinPoint){
        logger.info("[UPDATED] : Updated a record of {} - Updated values : {}" , joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getArgs()[0]);
    }

    @AfterReturning("delete()")
    public void logDelete(JoinPoint joinPoint){
        logger.info("[DELETED] : Deleted a record of {} - With ID : {}" , joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getArgs()[0]);
    }

}
