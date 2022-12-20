package com.mpb.salon.bookig.system.service;



import com.mpb.salon.bookig.system.entity.auth.User;
import com.mpb.salon.bookig.system.web.req.LoginRequest;
import com.mpb.salon.bookig.system.web.req.TokenRefreshRequest;
import com.mpb.salon.bookig.system.web.res.JwtResponse;

import javax.validation.constraints.Email;

public interface UserService{
    User getByEmail(String email);
    Boolean isUserNameExist(String userName);
    Boolean isActivated(@Email String email);
    Boolean activate(@Email String email);

    JwtResponse login(LoginRequest loginRequest);
    String refresh(TokenRefreshRequest refreshRequest);

    User addUser(User user);
}
