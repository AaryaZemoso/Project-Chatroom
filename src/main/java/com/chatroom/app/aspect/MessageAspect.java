package com.chatroom.app.aspect;

import com.chatroom.app.dto.message.MessageDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class MessageAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Before("execution(* com.chatroom.app.controller.MessageController.sendMessage(..))")
    public void beforeWritingMessages(JoinPoint joinPoint){

        logger.info("\n=====> [I] : Before Storing into message table");

        Object args[] = joinPoint.getArgs();
        for(Object arg : args){
            if(arg instanceof MessageDTO)
            logger.info("\n=========>[I] : \tUser : " + ((MessageDTO)arg).getUserId() + ", Message : "+ ((MessageDTO)arg).getMessage());
        }
    }
}
