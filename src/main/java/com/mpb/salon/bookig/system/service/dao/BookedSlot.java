package com.mpb.salon.bookig.system.service.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public interface BookedSlot {

    public Date getStart();

    public Date getEnd() ;
}
