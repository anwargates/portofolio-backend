package com.api.portofolio.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRes {
    private String name;
    private String email;
    private String token;
    private String refreshToken;
    private String expirationTime;

}
