package com.soft_kali.mfoodly.model;

import com.soft_kali.mfoodly.dto.cart.CartDto;
import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import lombok.*;

import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private List<CartDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean lastPage;
}
