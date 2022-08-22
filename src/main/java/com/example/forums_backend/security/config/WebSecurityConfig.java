package com.example.forums_backend.security.config;

import com.example.forums_backend.config.ApiAuthenticationFilter;
import com.example.forums_backend.config.ApiAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.forums_backend.config.route.constant.AccountRoute.PREFIX_ACCOUNT_ROUTE;
import static com.example.forums_backend.config.route.constant.AuthRoute.LOGIN_ROUTE;
import static com.example.forums_backend.config.route.constant.AuthRoute.PREFIX_AUTH_ROUTE;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApiAuthenticationFilter apiAuthenticationFilter = new ApiAuthenticationFilter(authenticationManagerBean());
        apiAuthenticationFilter.setFilterProcessesUrl(LOGIN_ROUTE);
        http.cors().and().csrf().disable();
        //route dành cho user không cần đang nhập
        http
                .authorizeRequests()
                .antMatchers(
                        PREFIX_AUTH_ROUTE.concat("/**"),
                        PREFIX_ACCOUNT_ROUTE.concat("/**")
                )
                .permitAll();
        //route quyền truy cập danh cho user đã đang nhập
        http
                .authorizeRequests()
                .antMatchers("/api/user-role/**", "/api/auth/user/**")
                .hasAnyAuthority("USER", "ADMIN");
        //route quyền truy cập dành cho admin
        http
                .authorizeRequests()
                .antMatchers("/api/admin-role/**")
                .hasAnyAuthority("ADMIN");
        http
                .addFilter(apiAuthenticationFilter);
        http
                .addFilterBefore(new ApiAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
