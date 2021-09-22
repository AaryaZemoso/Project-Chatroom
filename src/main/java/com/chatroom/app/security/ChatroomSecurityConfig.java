package com.chatroom.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.chatroom.app")
@EnableWebSecurity
public class ChatroomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource securityDatasource;

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception{
        return authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .jdbcAuthentication()
                    .dataSource(securityDatasource)
                    .passwordEncoder(passwordEncoder())
                    .usersByUsernameQuery("SELECT email, password, enabled from users where email = ?")
                    .authoritiesByUsernameQuery("SELECT username, authority from authorities where username = ?")
                    ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                .antMatchers("/register", "/resources/**")
                    .permitAll()
                .antMatchers("/api/**")
                    .hasRole("ADMIN")
            .antMatchers("/dashboard")
                .hasAnyRole("USER", "ADMIN")
            .anyRequest()
                .authenticated()

        .and()

            .formLogin()
                .loginPage("/login")
                    .loginProcessingUrl("/authenticateUser")
//                            .successForwardUrl("/dashboard")
                .permitAll()

        .and()

            .logout()
                .permitAll()

        .and()

            .httpBasic()
        ;

    }
}
