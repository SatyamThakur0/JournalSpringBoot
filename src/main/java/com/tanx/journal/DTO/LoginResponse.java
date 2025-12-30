package com.tanx.journal.DTO;


import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse {
    private String token;
    private Date date = new Date();

    public LoginResponse(String token){
        this.token = token;
    }
}
