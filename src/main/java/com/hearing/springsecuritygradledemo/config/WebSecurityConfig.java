package com.hearing.springsecuritygradledemo.config;

import com.hearing.springsecuritygradledemo.authentification.UserService;
import com.hearing.springsecuritygradledemo.filter.MyFilterSecurityInterceptor;
import com.hearing.springsecuritygradledemo.handle.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

import javax.sql.DataSource;

/**
 * Create by hearing on 18-9-2
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Bean
    public RememberMeServices rememberMeServices() {
        JdbcTokenRepositoryImpl rememberMeTokenRepository = new JdbcTokenRepositoryImpl();
        // 此处需要设置数据源，否则无法从数据库查询验证信息
        rememberMeTokenRepository.setDataSource(dataSource);

        PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices("remember",
                        new UserService(), rememberMeTokenRepository);

        // 该参数不是必须的，默认值为 "remember-me", 但如果设置必须和页面复选框的 name 一致
        rememberMeServices.setParameter("remember-me");
        return rememberMeServices;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()    // 禁用 Spring Security 自带的跨域处理,不然loginProcessingUrl配置失效
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/auth").authenticated()
                .antMatchers("/test").authenticated()
                .antMatchers("/admin").authenticated()
                .and()
                .rememberMe().rememberMeServices(rememberMeServices()).key("remember")
                .and()
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/loginConfirm")
                .usernameParameter("name").passwordParameter("password")
                .defaultSuccessUrl("/home").successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .logout().clearAuthentication(true).invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/home")
                .permitAll();
        http.exceptionHandling().accessDeniedPage("/deny");
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserService()).passwordEncoder(new BCryptPasswordEncoder());
    }
}
