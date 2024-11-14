package com.example.backendproject.repository;

import com.example.backendproject.domain.model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImagesRepository  extends JpaRepository<ProductImages, Long> {
    @Query("SELECT DISTINCT pi.imageUrl FROM ProductImages pi JOIN pi.product p WHERE p.productId = :productId")
    List<String> findDistinctImageUrlsByProductId(@Param("productId") Long productId);
}
