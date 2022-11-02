package com.dss.movie.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


/**
 * A UserInformation entity class to handle database to application data mapping.
 * origin table : auths.user_info
 */
@Entity
@Table(schema = "auths", name = "user_info")
public class UserInformation {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "admin_name")
    private String name;


    public UserInformation() {
    }

    public UserInformation(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public UserInformation(UUID id, String phoneNumber, String name) {
        this(phoneNumber, name);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
