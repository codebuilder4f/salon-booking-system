package com.mpb.salon.bookig.system.web.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSearch {
    public String name;
    public  String address;
    public String nic;
    public String email;
    public String mobileOne;
    public String mobileTwo;
}
