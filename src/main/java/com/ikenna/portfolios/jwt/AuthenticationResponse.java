package com.ikenna.portfolios.jwt;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private final String token;
    public AuthenticationResponse(String token){
        this.token = token;
    }

    public String getJwt(){
        return token;
    }
}
