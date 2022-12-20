package com.mpb.salon.bookig.system.web.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StockItemSearch {
    public String name;
    public Date from;
    public Date to;
//    public Date addedDate;
//    public Number price;
}
