package com.example.backendproject.repository;

import com.example.backendproject.domain.dto.forlist.ProductDTO;
import com.example.backendproject.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findAllByCategoryId(@Param("categoryId") Long categoryId);


    Product findProductByProductId(Long id);

    @Query("SELECT new com.example.backendproject.domain.dto.forlist.ProductDTO(" +
            "p.productId, p.productName, p.unitPrice, p.unitsInStock, p.discontinued, p.category.id, MIN(pi.imageUrl)) " +
            "FROM Product p " +
            "LEFT JOIN p.productImages pi " +
            "GROUP BY p.productId, p.productName, p.unitPrice, p.unitsInStock, p.discontinued, p.category.categoryName")
    List<ProductDTO> findAllProducts();


    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.category c " +
            "LEFT JOIN FETCH p.productImages pi " +
            "WHERE p.productId = :id")
    Optional<Product> findByIdWithCategoryAndImages(@Param("id") Long id);

    @Query("SELECT new com.example.backendproject.domain.dto.forlist.ProductDTO(" +
            "p.productId, p.productName, p.unitPrice, p.unitsInStock, p.discontinued, p.category.id, MIN(pi.imageUrl)) " +
            "FROM Product p " +
            "LEFT JOIN p.productImages pi " +
            "WHERE p.category.id= :id " +
            "GROUP BY p.productId, p.productName, p.unitPrice, p.unitsInStock, p.discontinued, p.category.categoryName")
    List<ProductDTO> findAllProductsByCategoryId(@Param("id") Long id);
}
