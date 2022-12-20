package com.mpb.salon.bookig.system.repository;

import com.mpb.salon.bookig.system.entity.Booking;
import com.mpb.salon.bookig.system.entity.type.BookingState;
import com.mpb.salon.bookig.system.service.dao.BookedSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> , JpaSpecificationExecutor<Booking> {
//    Optional<Booking> findAllByDate(TemporalType.TIMESTAMP date);
    List<Booking> findAllByStartEquals(Date start);
    List<BookedSlot> findAllByStartGreaterThanEqualAndEndLessThanEqualOrderByStart(Date start, Date end);
    List<Booking> findAllByStartGreaterThanEqualAndEndLessThanEqualAndBookingState(Date start, Date end, BookingState bookingState);

}
