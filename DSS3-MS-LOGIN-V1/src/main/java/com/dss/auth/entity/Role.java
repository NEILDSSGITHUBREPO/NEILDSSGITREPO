package com.dss.auth.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles", schema = "authorizations")
public class Role {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinTable(name = "role_permissions", schema = "authorizations"
            ,joinColumns = @JoinColumn(name = "role_id")
            ,inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

    public Role() {
    }

    public Role(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
