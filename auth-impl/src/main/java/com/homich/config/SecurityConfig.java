package com.homich.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Roman_Meerson
 */
@Configuration
@EnableAuthorizationServer
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
                .authenticated()
                .and()
                .formLogin().and()
                .exceptionHandling()
//				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
                .and().logout()
                .logoutSuccessUrl("/").permitAll().and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
