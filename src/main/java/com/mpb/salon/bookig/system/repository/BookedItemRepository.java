package com.mpb.salon.bookig.system.repository;

import com.mpb.salon.bookig.system.entity.BookedService;
import com.mpb.salon.bookig.system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookedItemRepository extends JpaRepository<BookedService, UUID> {
    void deleteAllByBookingAndIdNotIn(Booking booking, Collection<UUID> id);
}
