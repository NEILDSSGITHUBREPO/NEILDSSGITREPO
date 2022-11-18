package com.dss.auth.entity;

import javax.persistence.*;

@Entity
@Table(name = "actions", schema = "authorizations")
public class Action {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "value")
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
