package com.example.backendproject.domain.dto.forcreate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequest {
    private COrder order;
    private List<CartItem> cartItems;
}
