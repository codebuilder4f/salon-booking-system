package com.mpb.salon.bookig.system.web.controller;


import com.mpb.salon.bookig.system.entity.auth.Role;
import com.mpb.salon.bookig.system.entity.auth.User;
import com.mpb.salon.bookig.system.entity.auth.UserStatus;
import com.mpb.salon.bookig.system.entity.type.ERole;
import com.mpb.salon.bookig.system.repository.RoleRepository;
import com.mpb.salon.bookig.system.service.UserService;
import com.mpb.salon.bookig.system.web.api.Auth;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.req.LoginRequest;
import com.mpb.salon.bookig.system.web.req.RegisterRequest;
import com.mpb.salon.bookig.system.web.req.TokenRefreshRequest;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import com.mpb.salon.bookig.system.web.res.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AuthController implements Auth {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AuthController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @Override
    public ResponseEntity<ApiResponse> createRoles(List<ERole> roleList) {
        List<Role> roleList1 = roleList
                .stream()
                .map(eRole -> Role.builder().name(eRole).build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse
                .builder()
                .status(true)
                .timestamp(new Date())
                .body(roleRepository.saveAll(roleList1))
                .build());

    }

    @Override
    public ResponseEntity<ApiResponse> registerUser(RegisterRequest registerRequest) throws EntityNotFoundException {
        User user = new User();
        user.setUserName(registerRequest.getEmail());
        user.setUserStatus(UserStatus.ACTIVATED);
        user.setPassword(registerRequest.getPassword());
        Set<Role> roleList = registerRequest
                .getRoleList()
                .stream()
                .map(eRole -> {
                    Optional<Role> byName = roleRepository.findByName(eRole);
                    if (byName.isPresent()) {
                        return byName.get();
                    } else {
                        throw new EntityNotFoundException(Role.class, "id", eRole.toString());
                    }
                })
                .collect(Collectors.toSet());
        user.setRoles(roleList);

        User addUser = userService.addUser(user);


        return ResponseEntity.ok(ApiResponse
                .builder()
                .status(true)
                .timestamp(new Date())
                .message("register success")
                .body("")
                .build());

    }


    @Override
    public ResponseEntity<Map<String, String>> refreshToken(TokenRefreshRequest tokenRefreshRequest){
        Map<String, String> map = new HashMap<>();
        map.put("key", userService.refresh(tokenRefreshRequest));
        return ResponseEntity.ok(map);
    }
}
