package com.mpb.salon.bookig.system.web.req;

import com.mpb.salon.bookig.system.entity.type.BookingState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookingSearch {
    private BookingState bookingState;
    private String customerName;
}
