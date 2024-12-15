package com.example.backendproject.repository;

import com.example.backendproject.domain.dto.forlist.DiscountDTO;
import com.example.backendproject.domain.dto.forlist.UserDTO;
import com.example.backendproject.domain.dto.forlist.UserListDTO;
import com.example.backendproject.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

//    @Query("SELECT u FROM User u JOIN FETCH u.authorities a") // Fetch users with their authorities eagerly
//    Page<User> findAllUsers(Pageable pageable);

    @Query("SELECT new com.example.backendproject.domain.dto.forlist.UserListDTO(" +
            "u.id, u.username, u.email, u.phone, u.firstName, u.lastName) " +
            "FROM User u")
    Page<UserListDTO> findAllUsers(Pageable pageable);

}
