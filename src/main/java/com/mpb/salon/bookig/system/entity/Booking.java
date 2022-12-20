package com.mpb.salon.bookig.system.entity;

import com.mpb.salon.bookig.system.entity.type.BookingState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Booking {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;
    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDate;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column()
    private Double totalAmount;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn()
    private Customer customer;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingState bookingState;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AdvancedPayment> advancedPayments;

    @OneToMany(mappedBy = "booking", cascade = {CascadeType.ALL})
//    @JsonManagedReference
    private List<BookedService> bookedServices;

    public void setBookedServices(List<BookedService> bookedServices) {
        if (this.bookedServices == null) {
            this.bookedServices = new ArrayList<>();
        }
        Iterator<BookedService> bookedItemIterator = bookedServices.iterator();
        while (bookedItemIterator.hasNext()){
            BookedService next = bookedItemIterator.next();
            if (next.getBooking() == null){
                next.setBooking(this);
            }
        }
        this.bookedServices = bookedServices;
    }

    public void setAdvancedPayments(List<AdvancedPayment> advancedPayments) {
        if (this.advancedPayments == null) {
            this.advancedPayments = new ArrayList<>();
        }
        Iterator<AdvancedPayment> advancedPaymentIterator = advancedPayments.iterator();
        while (advancedPaymentIterator.hasNext()){
            AdvancedPayment next = advancedPaymentIterator.next();
            if (next.getBooking() == null){
                next.setBooking(this);
            }
        }
        this.advancedPayments = advancedPayments;
    }

}
