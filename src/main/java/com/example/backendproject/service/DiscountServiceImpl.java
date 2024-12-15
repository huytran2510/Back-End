package com.example.backendproject.service;

import com.example.backendproject.domain.dto.forcreate.CartItem;
import com.example.backendproject.domain.dto.forlist.DiscountDTO;
import com.example.backendproject.domain.dto.response.DiscountValidationResult;
import com.example.backendproject.domain.model.Discount;
import com.example.backendproject.domain.model.enums.DiscountType;
import com.example.backendproject.repository.DiscountRepository;
import com.example.backendproject.service.templates.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements IDiscountService {
    @Autowired
    private DiscountRepository discountRepository;
    public DiscountValidationResult validateCoupon(String couponCode, List<CartItem> cartItems, Double totalAmount) {
        Discount discount = discountRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Mã khuyến mãi không tồn tại."));

        // Kiểm tra ngày hiệu lực
        if (discount.getStartDate().isAfter(LocalDate.now()) || discount.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Mã khuyến mãi đã hết hạn.");
        }

        DiscountValidationResult result = new DiscountValidationResult();

        if (discount.getDiscountType() == DiscountType.GENERAL) {
            // Kiểm tra ngưỡng tổng tiền
            if (totalAmount < discount.getMinOrderAmount()) {
                result.setValid(false);
                result.setMessage("Hóa đơn chưa đạt ngưỡng tối thiểu để áp dụng khuyến mãi.");
            } else {
                result.setValid(true);
                result.setMessage("Áp dụng mã khuyến mãi thành công.");
                result.setDiscountAmount(totalAmount * discount.getDiscountPercent() / 100);
            }
        } else if (discount.getDiscountType() == DiscountType.PRODUCT) {
            // Kiểm tra sản phẩm trong giỏ hàng
            boolean hasEligibleProduct = cartItems.stream()
                    .anyMatch(item -> discount.getProducts().stream()
                            .anyMatch(product -> product.getProductId().equals(item.getProductId())));

            if (!hasEligibleProduct) {
                result.setValid(false);
                result.setMessage("Mã khuyến mãi không áp dụng cho các sản phẩm trong giỏ hàng.");
            } else {
                double discountAmount = cartItems.stream()
                        .filter(item -> discount.getProducts().stream()
                                .anyMatch(product -> product.getProductId().equals(item.getProductId())))
                        .mapToDouble(item -> item.getPrice() * item.getQuantity() * discount.getDiscountPercent() / 100)
                        .sum();

                result.setValid(true);
                result.setMessage("Áp dụng mã khuyến mãi thành công.");
                result.setDiscountAmount(discountAmount);
            }
        }

        return result;
    }

    public List<Discount> getAllCoupons() {
        return discountRepository.findAll(); // Đây là cách gọi của JPA repository, bạn có thể tuỳ chỉnh theo yêu cầu
    }

    public Page<DiscountDTO> getAllDiscount(int page, int size) {
        return discountRepository.findAllDiscount(PageRequest.of(page, size));
    }

    public long getTotalDiscounts() {
        return discountRepository.count(); // Lấy tổng số sản phẩm
    }


    public Optional<Discount> getDiscountById(Long id) {
        return discountRepository.findById(id);
    }

    public Discount createDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public Discount updateDiscount(Long id, Discount updatedDiscount) {
        return discountRepository.findById(id).map(discount -> {
            discount.setCouponCode(updatedDiscount.getCouponCode());
            discount.setDiscountDescription(updatedDiscount.getDiscountDescription());
            discount.setDiscountPercent(updatedDiscount.getDiscountPercent());
            discount.setStartDate(updatedDiscount.getStartDate());
            discount.setEndDate(updatedDiscount.getEndDate());
            discount.setMinOrderAmount(updatedDiscount.getMinOrderAmount());
            discount.setDiscountType(updatedDiscount.getDiscountType());
            return discountRepository.save(discount);
        }).orElseThrow(() -> new RuntimeException("Discount not found with id: " + id));
    }

    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }
}
