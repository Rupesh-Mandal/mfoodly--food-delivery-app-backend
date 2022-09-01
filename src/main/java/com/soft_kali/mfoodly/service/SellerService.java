package com.soft_kali.mfoodly.service;

import com.soft_kali.mfoodly.dto.seller.SellerDashboardData;
import com.soft_kali.mfoodly.dto.user.UserDto;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.JwtAuthRequest;
import com.soft_kali.mfoodly.model.ProductOrderResponse;
import org.springframework.data.domain.Pageable;

public interface SellerService {
    ApiResponse login(JwtAuthRequest request);

    UserDto getUSerInfo();

    SellerDashboardData getDashboardData();

    ApiResponse cancelOrder(Long productOrderId);

    ApiResponse acceptOrder(Long productOrderId);

    ProductOrderResponse getAllPendingOrder(Pageable pageable);

    ProductOrderResponse getAllStartedOrder(Pageable pageable);

    ProductOrderResponse getAllCancelledOrder(Pageable pageable);

    ProductOrderResponse getAllFailedOrder(Pageable pageable);

    ProductOrderResponse getAllOrderHistory(Pageable pageable);
}
