package com.mpb.salon.bookig.system.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookedService {

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
    private Date start;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column()
    private Double price;

    @JsonIgnore
    @ManyToOne()
//    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    @JoinColumn( nullable = false)
    private Booking booking;

    @ManyToOne()
    @JoinColumn( nullable = false)
    private Services services;
}
