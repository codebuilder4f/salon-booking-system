package com.mpb.salon.bookig.system.service;


import com.mpb.salon.bookig.system.entity.Booking;
import com.mpb.salon.bookig.system.service.dao.BookedSlot;
import com.mpb.salon.bookig.system.service.dao.BookedSlotStringDate;
import com.mpb.salon.bookig.system.service.dao.DayReport;
import com.mpb.salon.bookig.system.web.req.BookingSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface BookingService extends CRUDServices<Booking>{
    Page<Booking> search(BookingSearch bookingSearch, Pageable pageable);
    String getBill(UUID uuid);
    List<BookedSlotStringDate> getBookedTimes(Date date);
    List<DayReport> dayBookingCount(Date from, Date to);
    List<DayReport> dayIncome(Date from, Date to);
    List<DayReport>  approvedMonthlyIncomes(Date from, Date to);
}
