package com.dss.rest.repository;

import com.dss.rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT CASE WHEN Count(u) > 0 THEN true ELSE false END FROM User u " +
            "WHERE u.email = :email")
    Boolean emailExist(@Param("email") String email);

    @Query("SELECT CASE WHEN Count(u) > 0 THEN true ELSE false END FROM User u " +
            "WHERE u.userInformation.phoneNumber = :phoneNumber")
    Boolean phoneNumberExist(@Param("phoneNumber") String phoneNumber);

}
