package com.mpb.salon.bookig.system.web.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Getter
@Setter
public class FromTo {
    @NotBlank
    private Date from;

    @NotBlank
    private Date to;
}
