package com.appsdeveloperblog.photoappapiusers.ui.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
public class ExternalProperties {

    private String tokenSecret;
    private String tokenExpiration;
    private String loginUrl;

    @Autowired
    public ExternalProperties(@Value("${token.secret}") String tokenSecret,
                              @Value("${token.expiration}") String tokenExpiration,
                              @Value("${login.url.path}") String loginUrl) {
        this.tokenSecret = tokenSecret;
        this.tokenExpiration = tokenExpiration;
        this.loginUrl = loginUrl;
    }
}
