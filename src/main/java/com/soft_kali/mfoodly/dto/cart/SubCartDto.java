package com.soft_kali.mfoodly.dto.cart;

import com.soft_kali.mfoodly.dto.product.ProductDto;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubCartDto {
    private Long subCartId;

    private int quantity;

    private ProductDto productEntity;

}
