package com.dss.rest.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "actors", name = "actor")
public class Actor {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender", length = 1)
    private String gender;

    @Column(name = "age")
    private short age;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "actors"
            , name = "actor_movie"
            , joinColumns = @JoinColumn(name = "acid")
            , inverseJoinColumns = @JoinColumn(name = "mvid"))
    private Set<Movie> movies;

    public Actor() {
    }

    public Actor(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
