package com.soft_kali.mfoodly.model;

import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderResponse {
    private List<ProductOrderDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean lastPage;
}
