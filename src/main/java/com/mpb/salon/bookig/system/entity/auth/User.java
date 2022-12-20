package com.mpb.salon.bookig.system.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

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

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Email
    @Column(nullable = false)
    private String userName;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private Set<Role> roles = new HashSet<>();

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

}
