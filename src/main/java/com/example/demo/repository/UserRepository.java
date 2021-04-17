package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    User findUserById(Long id);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id != :userId")
    List<User> findAllExceptUserWithId(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.id != :userId")
    Page<User> findAll(@Param("userId") Long userId, Pageable pageable);

    @Query("Select u FROM User u WHERE lower(u.firstName) like lower(concat('%', :name,'%'))" +
            "OR lower(u.lastName) like lower(concat('%', :name,'%'))")
    Page<User> findSearchrResults(@Param("name") String name, Pageable pageable);
}
