package ru.mku.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.mku.services.authentication.DatabaseAuthenticationService;

@Configuration
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SpringSecurityWebAppConfig.class);

    @Autowired
    DatabaseAuthenticationService databaseAuthenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/**").hasRole("USER")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/index").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/api/login/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(databaseAuthenticationService);
    }

}
