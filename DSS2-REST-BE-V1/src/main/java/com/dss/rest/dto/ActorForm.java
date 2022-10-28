package com.dss.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO to handle user-end REST consumption for Actor Entity
 * Target Entity: Actor
 *
 * */
public class ActorForm {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String firstName;

    private String lastName;

    private String gender;

    private Short age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }
}
