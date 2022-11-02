package com.dss.review.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * A User entity class to handle database to application data mapping.
 * origin table : auths.user
 */
@Entity
@Table(schema = "auths", name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uif", referencedColumnName = "id")
    UserInformation userInformation;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, UserInformation userInformation) {
        this(email);
        this.userInformation = userInformation;
    }

    public User(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }
}
