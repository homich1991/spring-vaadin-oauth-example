package com.homich.auth.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 *
 */
@Configuration
@EnableAuthorizationServer
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).accessDeniedPage("/accessDenied")
                .and().authorizeRequests()
                .antMatchers("/VAADIN/**", "/PUSH/**", "/UIDL/**", "/login", "/login/**", "/error/**", "/accessDenied/**", "/vaadinServlet/**").permitAll()
                .antMatchers("/authorized", "/**").fullyAuthenticated()
//                .antMatchers("/index**", "/favicon.ico").permitAll()
//                .antMatchers(RestBaseUrl.API_ADMIN_URL + "/**").access("hasRole('ADMIN')")
//                .antMatchers(RestBaseUrl.API_URL + "/**").access("hasAnyRole('USER','ADMIN')")
//                .and()
//                .headers().frameOptions().disable()
//                .and()
//                .formLogin()
//                .successHandler(new CustomAuthenticationSuccessHandler())
//                .failureHandler(new CustomAuthenticationFailureHandler())
//                .loginProcessingUrl(RestBaseUrl.API_URL + "/authenticate").permitAll()
                .and()
                .logout().permitAll()
                .invalidateHttpSession(true)
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login")
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
