package com.dss.auth.repository;

import com.dss.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JpaRepository for User
 * Key: UUID
 * Entity: User
 *
 * @see User
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Repository method for checking if email is existing
     *
     * @Param String email
     * @Return Boolean ? true if exist : false if not
     */
    @Query("SELECT CASE WHEN Count(u) > 0 THEN true ELSE false END FROM User u " +
            "WHERE u.email = :email")
    Boolean emailExist(@Param("email") String email);

    /**
     * Repository method for checking if phoneNumber is existing
     *
     * @Param String phoneNumber
     * @Return Boolean ? true if exist : false if not
     */
    @Query("SELECT CASE WHEN Count(u) > 0 THEN true ELSE false END FROM User u " +
            "WHERE u.userInformation.phoneNumber = :phoneNumber")
    Boolean phoneNumberExist(@Param("phoneNumber") String phoneNumber);

    /**
     * Repository method for checking if email or PhoneNumber is existing
     *
     * @Param String s
     * @Return Optional<User>
     */
    @Query("SELECT u FROM User u WHERE u.email = :q OR u.userInformation.phoneNumber = :q")
    Optional<User> findByEmailOrPhoneNumber(@Param("q") String s);
}
