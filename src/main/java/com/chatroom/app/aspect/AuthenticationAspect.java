package com.chatroom.app.aspect;

import com.chatroom.app.dto.message.MessageDTO;
import com.chatroom.app.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class AuthenticationAspect {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Before("execution(* com.chatroom.app.controller.AppController.register(..))")
    public void checkRegistrationEndpoint(JoinPoint joinPoint){
        logger.info("\n=====> [I] : Before Storing into message table");

        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg instanceof User)
                logger.info("\n=========>[I] : \tUser : " + ((User)arg).toString());
        }
    }

    @After("execution(* com.chatroom.app.service.security.SecurityServiceImpl.autoLogin(..))")
    public void checkIfAuthenticated(){
        logger.info("\n[Security] Logged in as " + SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
