package com.mpb.salon.bookig.system.web.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {
    private String token;
}
