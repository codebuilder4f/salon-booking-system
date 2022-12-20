package com.mpb.salon.bookig.system.web.req;

import com.mpb.salon.bookig.system.entity.type.ERole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private List<ERole> roleList;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
