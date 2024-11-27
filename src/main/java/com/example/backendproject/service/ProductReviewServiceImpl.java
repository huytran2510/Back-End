package com.example.backendproject.service;

import com.example.backendproject.domain.dto.forlist.ProductReviewDTO;
import com.example.backendproject.domain.model.Customer;
import com.example.backendproject.domain.model.Product;
import com.example.backendproject.domain.model.ProductReview;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.repository.CustomerRepository;
import com.example.backendproject.repository.ProductRepository;
import com.example.backendproject.repository.ProductReviewRepository;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.service.templates.IProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductReviewServiceImpl implements IProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<ProductReviewDTO> getReviewsByProductId(Long productId) {
        List<ProductReview> reviews = productReviewRepository.findReviewsByProductId(productId);
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ProductReviewDTO addReview(Long productId, Long customerId, Integer rating, String reviewText) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (productOpt.isEmpty() || customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Sản phẩm hoặc người dùng không tồn tại!");
        }

        ProductReview review = new ProductReview();
        review.setProduct(productOpt.get());
        review.setCustomer(customerOpt.get());
        review.setRating(rating);
        review.setReviewText(reviewText);

        ProductReview savedReview = productReviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    private ProductReviewDTO convertToDTO(ProductReview review) {
        ProductReviewDTO dto = new ProductReviewDTO();
        dto.setReviewId(review.getReviewId());
        dto.setProductId(review.getProduct().getProductId());
        dto.setCustomerId(review.getCustomer().getCustomerId());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());
        dto.setCustomerName(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName());
        dto.setReviewDate(review.getReviewDate());
        return dto;
    }
}
