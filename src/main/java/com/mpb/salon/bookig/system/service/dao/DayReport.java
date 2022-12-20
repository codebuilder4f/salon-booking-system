package com.mpb.salon.bookig.system.service.dao;

import java.math.BigDecimal;

public class DayReport {
    private String day;
    private BigDecimal amount;

    public DayReport(String day, BigDecimal amount) {
        this.day = day;
        this.amount = amount;
    }

    public String getDay() {
        return day;
    }


    public String getMonth() {
        String[] split = day.split("-");
        return split[0]+"-"+ split[1];
    }

    public void setDay(String day) {
        this.day = day;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
