package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup", "/login", "/h2/**", "/css/**", "/js/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.formLogin()
                .loginPage("/login")
                .permitAll();
        http.formLogin()
                .defaultSuccessUrl("/home", true);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/h2/**", "/signup", "/css/**", "/js/**")  // TODO: QUITAR "/h2/**", SI FINALEMENTE NO ES NECESARIO
//                .permitAll().anyRequest().authenticated();
//
//        http.formLogin()
//                .loginPage("/login")
//                .permitAll();
//
//        http.formLogin()
//                .defaultSuccessUrl("/home", true);
//
//        http.csrf().disable(); // To see H2 tables  // TODO: QUITAR "/h2/**", SI FINALEMENTE NO ES NECESARIO
//
//        http.headers().frameOptions().disable(); // To see H2 tables    // TODO: QUITAR "/h2/**", SI FINALEMENTE NO ES NECESARIO
//    }
}
