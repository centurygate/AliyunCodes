package com.xigeng.waterfactory.controller;

/**
 * Created by free on 2016/11/12.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    DataSource dataSourceforauth;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("yiibai").password("123456").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("dba").password("123456").roles("ADMIN","DBA");
        System.out.println(dataSourceforauth);
        System.out.println(dataSourceforauth.getConnection().toString());
        auth.jdbcAuthentication().dataSource(dataSourceforauth)
                .usersByUsernameQuery
                (
                " select username,password,status as enabled "+
                " from security_user "+
                " where username=? "
                )
                .authoritiesByUsernameQuery
                (
                " select u.username,r.name as authority "+
                " from security_user u "+
                " join security_user_role ur "+
                " on u.id=ur.user_id "+
                " join security_role r "+
                " on r.id=ur.role_id "+
                " where u.username=? "
                );

    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception
//    {
//        super.configure(auth);
//        System.out.println(dataSource);
//        System.out.println(dataSource.getConnection().toString());
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery
//                        (
//                                "select username,password,status as enabled"+
//                                        "from security_user"+
//                                        "where username=?"
//                        )
//                .authoritiesByUsernameQuery
//                        (
//                                "select u.username,r.name as authority"+
//                                        "from security_user u"+
//                                        "join security_user_role ur"+
//                                        "on u.id=ur.user_id"+
//                                        "join security_role r"+
//                                        "on r.id=ur.role_id"+
//                                        "where u.username=?"
//                        );
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home").access("hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/db/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_DBA')")
                .and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
                .usernameParameter("ssoId").passwordParameter("password")
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }

}
