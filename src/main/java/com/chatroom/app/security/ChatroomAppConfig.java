package com.chatroom.app.security;

import com.chatroom.app.exception.security.DriverNotFoundException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:application.properties")
public class ChatroomAppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public ViewResolver viewResolver(){

        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");

        return resolver;
    }

    @Bean
    public DataSource securityDatasource(){

        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

        try{
            securityDataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch(PropertyVetoException pe){
            throw new DriverNotFoundException(pe);
        }

        securityDataSource.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        securityDataSource.setUser(environment.getProperty("spring.datasource.username"));
        securityDataSource.setPassword(environment.getProperty("spring.datasource.password"));

        securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));


        return securityDataSource;
    }

    private int getIntProperty(String propName)
    {
        String propVal = environment.getProperty(propName);
        return Integer.parseInt(propVal);
    }
}
