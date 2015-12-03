package com.smg.portal.config;

import com.smg.portal.security.*;
import com.smg.portal.web.filter.CsrfCookieGeneratorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import org.springframework.security.web.csrf.CsrfFilter;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private Environment env;

    @Inject
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Inject
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Inject
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/scripts/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/assets/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .ignoringAntMatchers("/websocket/**")
        .and()
            .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .formLogin()
            .loginProcessingUrl("/rest/authentication")
            .successHandler(ajaxAuthenticationSuccessHandler)
            .failureHandler(ajaxAuthenticationFailureHandler)
            .usernameParameter("j_username")
            .passwordParameter("j_password")
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/rest/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .permitAll()
        .and()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .authorizeRequests()
            .antMatchers("/rest/register").permitAll()
            .antMatchers("/rest/activate").permitAll()
            .antMatchers("/rest/authenticate").permitAll()
            .antMatchers("/rest/account/reset_password/init").permitAll()
            .antMatchers("/rest/account/reset_password/finish").permitAll()
            .antMatchers("/rest/logs/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/rest/audits/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/rest/**").authenticated()
            .antMatchers("/metrics/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/health/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/dump/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/shutdown/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/beans/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/configprops/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/info/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/autoconfig/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/env/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/mappings/**").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/v2/api-docs/**").permitAll()
            .antMatchers("/configuration/security").permitAll()
            .antMatchers("/configuration/ui").permitAll()
            .antMatchers("/swagger-ui/index.html").hasAuthority(RoleConstants.ADMIN)
            .antMatchers("/protected/**").authenticated() ;

    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
