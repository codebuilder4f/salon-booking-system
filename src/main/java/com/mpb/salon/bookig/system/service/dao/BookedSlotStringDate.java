package com.mpb.salon.bookig.system.service.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Data
@Getter
@Setter
public
class BookedSlotStringDate{
    private String start;
    private String end;

    public BookedSlotStringDate(Date start, Date end) {
        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        TimeZone utc = TimeZone.getTimeZone("UTC");
        simpleDateFormat.setTimeZone(utc);
        
        this.start = simpleDateFormat.format(start);
        this.end = simpleDateFormat.format(end);
    }
}
