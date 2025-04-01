package com.example.SpringBootDatabase.repository;

import com.example.SpringBootDatabase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT s FROM User s WHERE s.email = :email OR s.name LIKE %:name%")
    List<User> findByEmailOrName(@Param("email") String email, @Param("name") String name);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsUserByUserId(String userId);

}
