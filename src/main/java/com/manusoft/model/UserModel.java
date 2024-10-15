package com.manusoft.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "stock_user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;

    @Column(unique = true, nullable = false)
    private String userName;

    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile_number")
    private Long mobileNumber;

    @Column(name = "last_login")
    @UpdateTimestamp
    private LocalDateTime lastLogin;

    @Column(name = "last_password_change")
    @UpdateTimestamp
    private LocalDateTime lastPasswordChange;

    @Column(name = "password_change")
    private boolean passwordChange;

    @Column(name = "enable_email_updates")
    private boolean enableEmailUpdates;
    //private Set<Roles> role = new LinkedHashSet<>();
    private String role;
}
