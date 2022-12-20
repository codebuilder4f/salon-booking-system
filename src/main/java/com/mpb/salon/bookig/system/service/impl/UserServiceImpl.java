package com.mpb.salon.bookig.system.service.impl;

import com.mpb.salon.bookig.system.entity.auth.User;
import com.mpb.salon.bookig.system.entity.auth.UserStatus;
import com.mpb.salon.bookig.system.repository.RoleRepository;
import com.mpb.salon.bookig.system.repository.UserRepository;
import com.mpb.salon.bookig.system.security.jwt.JwtUtils;
import com.mpb.salon.bookig.system.security.services.UserDetailsImpl;
import com.mpb.salon.bookig.system.security.services.UserDetailsServiceImpl;
import com.mpb.salon.bookig.system.service.UserService;
import com.mpb.salon.bookig.system.web.exception.EmailAlreadyExistException;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.exception.ExceptionWithMessage;
import com.mpb.salon.bookig.system.web.req.LoginRequest;
import com.mpb.salon.bookig.system.web.req.TokenRefreshRequest;
import com.mpb.salon.bookig.system.web.res.JwtResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final  UserDetailsServiceImpl userDetailsService;
;
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder encoder,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> byUserName = userRepository.findByUserName(email);
        if (byUserName.isEmpty()) {
            throw new EntityNotFoundException(User.class, "email", email);
        }
        return byUserName.get();
    }

    @Override
    public Boolean isUserNameExist(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    @Override
    public Boolean isActivated(String email) {
        return this.getByEmail(email).getUserStatus().equals(UserStatus.ACTIVATED);
    }

    @Override
    public Boolean activate(String email) {
        return Boolean.FALSE; // TODO
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        if (!this.isActivated(loginRequest.getUserName())){
            throw new ExceptionWithMessage("This account is deactivated");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String jwtRefresh = jwtUtils.generateJwtRefreshToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return JwtResponse
                .builder()
                .type("Bearer")
                .id(userDetails.getId())
                .roles(roles)
                .refreshToken(jwtRefresh)
                .token(jwt)
                .username(userDetails.getUsername())
                .build();
    }

    @Override
    public User addUser(User user) {
        if (this.isUserNameExist(user.getUserName())) {
            throw new EmailAlreadyExistException();
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    @Override
    public String refresh(TokenRefreshRequest refreshRequest) {

        Boolean isValid = jwtUtils.validateJwtRefreshToken(refreshRequest.getToken());
        if (isValid){
            String email = jwtUtils.getEmailFromJwtRefreshToken(refreshRequest.getToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            return "Bearer " + jwtUtils.generateJwtToken(authentication);
        }
        return null;
    }
}
