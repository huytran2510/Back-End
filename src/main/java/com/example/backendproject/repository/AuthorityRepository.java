package com.example.backendproject.repository;

import com.example.backendproject.domain.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    List<Authority> findByUserId(long id);
    Set<Authority> findByUserId(Long userId);
}
