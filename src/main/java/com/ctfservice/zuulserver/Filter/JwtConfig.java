package com.ctfservice.zuulserver.Filter;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
    public String getUri() {
        return Uri;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getSecret() {
        return secret;
    }

    @Value("${security.jwt.uri:/auth/**}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{3600000}}")
    private int expiration;

    @Value("${security.jwt.secret:secret}")
    private String secret;


}
