package com.soft_kali.mfoodly.dto;


import com.soft_kali.mfoodly.entity.ProductEntity;
import com.soft_kali.mfoodly.entity.UserEntity;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
