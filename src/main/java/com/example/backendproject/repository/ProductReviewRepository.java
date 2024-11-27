package com.example.backendproject.repository;

import com.example.backendproject.domain.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    @Query("SELECT r FROM ProductReview r WHERE r.product.productId = :productId")
    List<ProductReview> findReviewsByProductId(@Param("productId") Long productId);}
