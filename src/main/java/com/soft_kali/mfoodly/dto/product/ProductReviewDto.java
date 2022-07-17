package com.soft_kali.mfoodly.dto.product;


import com.soft_kali.mfoodly.dto.user.UserDto;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDto {
    private Long productReviewId;


    private String reviewComment;
    private int rating;
    private ProductDto productEntity;

    private UserDto userEntity;

}
