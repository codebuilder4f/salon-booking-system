package com.mpb.salon.bookig.system.web.api;


import com.mpb.salon.bookig.system.entity.type.ERole;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.req.LoginRequest;
import com.mpb.salon.bookig.system.web.req.RegisterRequest;
import com.mpb.salon.bookig.system.web.req.TokenRefreshRequest;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import com.mpb.salon.bookig.system.web.res.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static com.mpb.salon.bookig.system.Application.API_V;


@RequestMapping(path = "/"+API_V+"/auth")
public interface Auth {

    @PostMapping(path = "/refresh")
    ResponseEntity<Map<String, String>> refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest);


    @PostMapping(path = "/login")
    ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/role")
    ResponseEntity<ApiResponse> createRoles(@RequestBody List<ERole> roleList);

    @PostMapping("/sign-up")
    ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
            throws EntityNotFoundException;

}
