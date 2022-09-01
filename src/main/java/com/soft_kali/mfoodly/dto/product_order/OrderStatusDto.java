package com.soft_kali.mfoodly.dto.product_order;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderStatusDto {
    private int orderStatusId;

    String name;
    String statusColorCode;
}
