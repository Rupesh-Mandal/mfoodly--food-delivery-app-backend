package com.soft_kali.mfoodly.dto.seller;

import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import lombok.*;

import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDashboardData {

    public long totalPendingOrder;
    public long totalStartedOrder;
    public long totalProduct;
    public long totalOutlet;

    List<ProductOrderDto> productOrderDtoList;
}
