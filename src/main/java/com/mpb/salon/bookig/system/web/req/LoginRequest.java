package com.mpb.salon.bookig.system.web.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {
    @Email
    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
