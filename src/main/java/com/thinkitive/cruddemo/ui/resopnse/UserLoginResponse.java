package com.thinkitive.cruddemo.ui.resopnse;

public class UserLoginResponse {

    private String token;

    public UserLoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
