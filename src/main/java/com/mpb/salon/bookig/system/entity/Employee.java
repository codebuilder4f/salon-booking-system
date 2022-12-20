package com.mpb.salon.bookig.system.entity;


import com.mpb.salon.bookig.system.entity.type.EmployeeState;
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
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Employee {

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

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private Double monthlyPayment;


    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeState employeeState;
}
