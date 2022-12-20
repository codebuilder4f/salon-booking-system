package com.mpb.salon.bookig.system.web.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class JwtResponse {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private UUID id;
    private String username;
    private List<String> roles;
}
