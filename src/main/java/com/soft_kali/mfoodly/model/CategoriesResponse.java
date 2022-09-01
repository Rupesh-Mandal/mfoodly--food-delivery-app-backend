package com.soft_kali.mfoodly.model;

import com.soft_kali.mfoodly.dto.cart.CartDto;
import com.soft_kali.mfoodly.dto.product.CategoryDto;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesResponse {
    private List<CategoryDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean lastPage;
}
