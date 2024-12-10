package com.example.backendproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountValidationResult {
    private boolean valid; // Mã khuyến mãi hợp lệ hay không
    private String message; // Thông báo chi tiết
    private Double discountAmount;
}
