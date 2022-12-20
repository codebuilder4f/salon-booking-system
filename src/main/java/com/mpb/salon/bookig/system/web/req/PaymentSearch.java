package com.mpb.salon.bookig.system.web.req;

import com.mpb.salon.bookig.system.entity.type.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentSearch {
    public Double amount;
    public Date from;
    public Date to;
    public PaymentType paymentType;
}
