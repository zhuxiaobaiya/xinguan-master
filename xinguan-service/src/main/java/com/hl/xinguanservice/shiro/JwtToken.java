package com.hl.xinguanservice.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author huangliang
 * @Date 2021/11/15 10:42
 * @Version 1.0
 * @Description
 */
public class JwtToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
