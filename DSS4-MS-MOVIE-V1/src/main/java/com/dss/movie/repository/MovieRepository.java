package com.dss.movie.repository;

import com.dss.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
}