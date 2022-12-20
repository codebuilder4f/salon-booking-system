package com.mpb.salon.bookig.system.service.impl;

import com.mpb.salon.bookig.system.entity.AdvancedPayment;
import com.mpb.salon.bookig.system.entity.BookedService;
import com.mpb.salon.bookig.system.entity.Booking;
import com.mpb.salon.bookig.system.entity.type.BookingState;
import com.mpb.salon.bookig.system.repository.BookedItemRepository;
import com.mpb.salon.bookig.system.repository.BookingRepository;
import com.mpb.salon.bookig.system.service.BookingService;
import com.mpb.salon.bookig.system.service.CustomerService;
import com.mpb.salon.bookig.system.service.dao.BookedSlotStringDate;
import com.mpb.salon.bookig.system.service.dao.DayReport;
import com.mpb.salon.bookig.system.service.specifications.BookingSpecification;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import com.mpb.salon.bookig.system.web.exception.ExceptionWithMessage;
import com.mpb.salon.bookig.system.web.req.BookingSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final BookedItemRepository bookedItemRepository;
    public final CustomerService customerService;

    public BookingServiceImpl(BookingRepository repository, BookedItemRepository bookedItemRepository, CustomerService customerService) {
        this.repository = repository;
        this.bookedItemRepository = bookedItemRepository;
        this.customerService = customerService;
    }

    @Override
    public Booking get(UUID uuid) {
        Optional<Booking> booking = repository.findById(uuid);
        if (booking.isPresent()) {
            return booking.get();
        } else {
            throw new EntityNotFoundException(Booking.class, "id", uuid.toString());
        }
    }

    @Override
    @Transactional
    public Booking create(Booking booking) {
        Double realTotalAmount = booking.getBookedServices().stream().parallel().map(BookedService::getPrice).reduce((double) 0, Double::sum);
        if (realTotalAmount > booking.getTotalAmount()){
            booking.setTotalAmount(realTotalAmount);
        }
        if (booking.getAdvancedPayments().size() > 0){
            booking.setBookingState(BookingState.ADVANCED);
        }else {
            booking.setBookingState(BookingState.PENDING);
        }
        if (booking.getCustomer().getId() != null){
            booking.setCustomer(customerService.get(booking.getCustomer().getId()));
        }

        // Getting updated advance payment (in db + newly added from front-end)
        Double totalPaid = booking.getAdvancedPayments().stream().parallel().map(AdvancedPayment::getAmount).reduce((double) 0, Double::sum);
        if (totalPaid >= booking.getTotalAmount()) { // Automatically make the rent complete
            booking.setBookingState(BookingState.COMPLETED);
        }

        return repository.save(booking);
    }

    @Override
    public Booking update(UUID uuid, Booking booking) {
        Double realTotalAmount = booking.getBookedServices().stream().parallel().map(BookedService::getPrice).reduce((double) 0, Double::sum);
        if (realTotalAmount > booking.getTotalAmount()){
            booking.setTotalAmount(realTotalAmount);
        }

        // Getting updated advance payment (in db + newly added from front-end)
        Double totalPaid = booking.getAdvancedPayments().stream().parallel().map(AdvancedPayment::getAmount).reduce((double) 0, Double::sum);


        // Handle complete request from front-end
        if (booking.getBookingState() == BookingState.COMPLETED && booking.getTotalAmount() > totalPaid){
            throw new ExceptionWithMessage("Total amount is not paid yet, Please pay the full amount of "+booking.getTotalAmount()+" complete the rent");
        }
        if (totalPaid >= booking.getTotalAmount()) { // Automatically make the rent complete
            booking.setBookingState(BookingState.COMPLETED);
        }else if (totalPaid > 0){
            booking.setBookingState(BookingState.ADVANCED);
        }
        booking.setId(uuid);
        Booking saved = repository.save(booking);
        List<UUID> collect = booking.getBookedServices().stream().map(BookedService::getId).collect(Collectors.toList());
        this.bookedItemRepository.deleteAllByBookingAndIdNotIn(saved,collect);
        return saved;
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Booking> booking = repository.findById(uuid);
        if (booking.isPresent()) {
            repository.delete(booking.get());
        } else {
            throw new EntityNotFoundException(Booking.class, "id", uuid.toString());
        }
    }

    @Override
    public List<Booking> createAll(List<Booking> list) {
        return list.stream().parallel().map(this::create).collect(Collectors.toList());
    }

    @Override
    public Page<Booking> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Booking> search(BookingSearch bookingSearch, Pageable pageable) {
        return repository.findAll(new BookingSpecification(bookingSearch), pageable);
    }

    @Override
    public String getBill(UUID uuid) {
        Booking booking = this.get(uuid);
        UUID uuid1=UUID.randomUUID();
        return BillGenerator.generate(booking, uuid1.toString());
    }

    @Override
    public List<BookedSlotStringDate> getBookedTimes(Date date) {
        Date end = new Date(date.toLocaleString());
        end.setDate(date.getDate() + 1);
        return repository.findAllByStartGreaterThanEqualAndEndLessThanEqualOrderByStart(date, end)
                .stream().map(bookedSlot -> new BookedSlotStringDate(bookedSlot.getStart(), bookedSlot.getEnd()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DayReport> dayBookingCount(Date from, Date to) {
        List<Booking> rents = repository.findAllByStartGreaterThanEqualAndEndLessThanEqualAndBookingState(from, to, BookingState.ADVANCED);
        List<Booking> rents1 = repository.findAllByStartGreaterThanEqualAndEndLessThanEqualAndBookingState(from, to, BookingState.COMPLETED);
        List<Booking> rents2 = repository.findAllByStartGreaterThanEqualAndEndLessThanEqualAndBookingState(from, to, BookingState.PENDING);

        rents.addAll(rents1);
        rents.addAll(rents2);

        return rents
                .stream()
                .map(item -> new DayReport(item.getStart().toString().split(" ")[0], BigDecimal.valueOf(item.getTotalAmount())))
                .collect(Collectors.groupingBy(DayReport::getDay))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()))
                .entrySet()
                .stream().map(v -> new DayReport(v.getKey(), BigDecimal.valueOf(v.getValue())))
                .collect(Collectors.toList())
                ;
    }

    @Override
    public List<DayReport> dayIncome(Date from, Date to) {
        List<Booking> rents = repository.findAllByStartGreaterThanEqualAndEndLessThanEqualAndBookingState(from, to, BookingState.COMPLETED);
        return rents
                .stream()
                .map(item -> new DayReport(item.getStart().toString().split(" ")[0], BigDecimal.valueOf(item.getTotalAmount())))
                .collect(
                        Collectors.toMap(
                                DayReport::getDay,
                                Function.identity(),
                                (sum1, sum2) -> new DayReport(
                                        sum1.getDay(),
                                        sum1.getAmount().add(sum2.getAmount())
                                )
                        )
                )
                .values()
                .stream()
                .sorted(Comparator.comparing(DayReport::getDay))
                .collect(Collectors.toList());

    }

    @Override
    public List<DayReport> approvedMonthlyIncomes(Date from, Date to) {
        List<Booking> rents = repository.findAllByStartGreaterThanEqualAndEndLessThanEqualAndBookingState(from, to, BookingState.COMPLETED);

        return rents
                .stream()
                .map(item -> new DayReport(item.getStart().toString().split(" ")[0], BigDecimal.valueOf(item.getTotalAmount())))
                .collect(
                        Collectors.toMap(
                                DayReport::getMonth,
                                Function.identity(),
                                (sum1, sum2) -> new DayReport(
                                        sum1.getDay(),
                                        sum1.getAmount().add(sum2.getAmount())
                                )
                        )
                )
                .values()
                .stream()
                .sorted(Comparator.comparing(DayReport::getDay))
                .collect(Collectors.toList());
    }
}

