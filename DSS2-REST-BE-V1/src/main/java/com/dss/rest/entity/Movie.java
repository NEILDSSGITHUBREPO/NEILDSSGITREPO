package com.dss.rest.entity;

import com.dss.rest.entity.types.Category;
import com.dss.rest.entity.types.MaturityRating;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "movs", name = "movies")
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "budget")
    private double budget;

    @Column(name = "income")
    private double income;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "trailer_link")
    private String trailerLink;

    @Column(name = "maturity_rating")
    @Enumerated
    private MaturityRating maturityRating;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(schema = "movs", name = "movie_category", joinColumns = @JoinColumn(name = "mvid"))
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Set<Category> categories;

    @OneToMany(mappedBy = "reviewedMovie")
    private Set<Review> reviews;
    @ManyToOne
    @JoinColumn(name = "added_by")
    private User addedBy;

    public Movie() {
    }

    public Movie(UUID id) {
        this.id = id;
    }

    public Movie(String title, double budget, LocalDate releaseDate) {
        this.title = title;
        this.budget = budget;
        this.releaseDate = releaseDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public MaturityRating getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(MaturityRating maturityRating) {
        this.maturityRating = maturityRating;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
