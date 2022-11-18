package com.dss.auth.dto;


public class TokenForm {

    private String token;
    private boolean match;

    public TokenForm() {
    }

    public TokenForm(String token, boolean match) {
        this.token = token;
        this.match = match;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
