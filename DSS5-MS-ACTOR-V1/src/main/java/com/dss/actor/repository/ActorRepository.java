package com.dss.actor.repository;

import com.dss.actor.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<Actor, UUID> {
    Page<Actor> findAllByMoviesId(UUID mvid, Pageable pageable);
}
