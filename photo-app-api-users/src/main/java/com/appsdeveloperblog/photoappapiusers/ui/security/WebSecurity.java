package com.appsdeveloperblog.photoappapiusers.ui.security;

import com.appsdeveloperblog.photoappapiusers.ui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private String tokenSecret;
    private String tokenExpiration;
    private String loginUrl;
    private BCryptPasswordEncoder encoder;
    private UserService userService;

    @Autowired
    public WebSecurity(@Value("${token.secret}") String tokenSecret,
                       @Value("${token.expiration}") String tokenExpiration,
                       @Value("${login.url.path}") String loginUrl,
                       BCryptPasswordEncoder encoder, UserService userService) {
        this.tokenSecret = tokenSecret;
        this.tokenExpiration = tokenExpiration;
        this.loginUrl = loginUrl;
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .addFilter(getAuthenticationFilter())
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll();

    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter(userService, tokenSecret, tokenExpiration, authenticationManager());
        filter.setFilterProcessesUrl(loginUrl);
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }
}
