package com.zuora.usagedatamapper.springConfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.zuora.usagedatamapper.util.Constants.API_INSTANCE_CONFIGS;
import static com.zuora.usagedatamapper.util.Constants.API_MAPPINGS;
import static com.zuora.usagedatamapper.util.Constants.API_SCHEDULES;
import static com.zuora.usagedatamapper.util.Constants.API_SFTP_CONNECTIONS;

@Configuration
public class AppSecurity extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

/*
    private final UserDetailsService userDetailsService;

    public AppSecurity(@Qualifier("jpaUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.userDetailsService(userDetailsService);
//        auth.jdbcAuthentication()
//                .dataSource(null)
//                .withDefaultSchema()
//                .withUser(
//                    User.withUsername("user")
//                                .password("blah")
//                                .roles(USER)
//                )
//                .withUser(
//                    User.withUsername("admin")
//                            .password("admin")
//                            .roles(ADMIN)
//                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.formLogin()
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.DELETE, API_INSTANCE_CONFIGS).hasRole(ADMIN)
//                .antMatchers(HttpMethod.DELETE, API_SCHEDULES).hasRole(ADMIN)
//                .antMatchers(HttpMethod.DELETE, API_SFTP_CONNECTIONS).hasRole(ADMIN)
//                .antMatchers(HttpMethod.DELETE, API_MAPPINGS).hasAnyRole(ADMIN, USER)
//                .antMatchers(HttpMethod.POST, API_INSTANCE_CONFIGS).hasRole(ADMIN)
//
//                .antMatchers(API_SCHEDULES).hasAnyRole(ADMIN, USER)
//                .antMatchers(API_SFTP_CONNECTIONS).hasAnyRole(ADMIN, USER)
//                .antMatchers(API_MAPPINGS).hasAnyRole(ADMIN, USER)
//                .antMatchers(API_INSTANCE_CONFIGS).hasAnyRole(ADMIN, USER);
    }
}
