package com.dss.review.repository;

import com.dss.review.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
}