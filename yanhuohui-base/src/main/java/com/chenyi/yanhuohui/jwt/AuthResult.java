package com.chenyi.yanhuohui.jwt;

import lombok.Data;

@Data
public class AuthResult {
    private int code;
    private String msg;
    private String token;
    private String refreshToken;


    public AuthResult(int i, String s) {
        this.code = i;
        this.msg = s;
    }

    public AuthResult(int i, String success, String token, String refreshToken) {
        this.code = i;
        this.msg = success;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
