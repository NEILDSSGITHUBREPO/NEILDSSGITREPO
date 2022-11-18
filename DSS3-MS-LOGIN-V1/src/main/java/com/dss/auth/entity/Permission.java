package com.dss.auth.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "permissions", schema = "authorizations")
public class Permission {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinTable(name = "permission_resources", schema = "authorizations"
            ,joinColumns = @JoinColumn(name = "permission_id")
            ,inverseJoinColumns = @JoinColumn(name = "resource_id"))
    List<Resource> resources;

    @OneToMany
    @JoinTable(name = "permission_actions", schema = "authorizations"
            ,joinColumns = @JoinColumn(name = "permission_id")
            ,inverseJoinColumns = @JoinColumn(name = "action_id"))
    List<Action> actions;

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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
