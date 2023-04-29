package com.PooValBha.bookstore.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource securityDataSource;

    public SecurityConfig(DataSource securityDataSource) {
        this.securityDataSource = securityDataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(securityDataSource).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests(requests -> requests
                .antMatchers("/").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/cart/**").permitAll()
                .antMatchers("/book/**").hasAuthority("ADMIN"))
            .formLogin(login -> login
                .loginPage("/login")
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/"));
        http.headers().frameOptions().disable();
    }
}
