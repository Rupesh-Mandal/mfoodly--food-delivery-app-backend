package com.soft_kali.mfoodly.dto.admin;

import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import com.soft_kali.mfoodly.dto.user.UserDto;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardData {
    public long totalCustomer;
    public long totalSeller;
    public long totalCategories;
    public long totalOutlet;

    List<ProductOrderDto> productOrderEntityList;
    List<UserDto> customerList;
    List<UserDto> sellerList;
}
