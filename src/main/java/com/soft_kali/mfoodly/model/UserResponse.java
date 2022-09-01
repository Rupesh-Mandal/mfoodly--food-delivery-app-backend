package com.soft_kali.mfoodly.model;

import com.soft_kali.mfoodly.dto.product.ProductDto;
import com.soft_kali.mfoodly.dto.user.UserDto;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private List<UserDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean lastPage;
}
