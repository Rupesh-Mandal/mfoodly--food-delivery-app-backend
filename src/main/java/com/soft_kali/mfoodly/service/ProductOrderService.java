package com.soft_kali.mfoodly.service;

import com.soft_kali.mfoodly.dto.product_order.ProductOrderDto;
import com.soft_kali.mfoodly.model.ApiResponse;
import com.soft_kali.mfoodly.model.ProductOrderResponse;
import org.springframework.data.domain.Pageable;

public interface ProductOrderService {

    ApiResponse startNewOrder(ProductOrderDto productOrderDto, int cityId,Long addressBookId);

    ApiResponse acceptOrderByOutlet(Long productOrderId,Long outletId);
    ApiResponse cancelOrderByOutlet(Long productOrderId,Long outletId);
    ApiResponse cancelOrderUser(Long productOrderId);

    ApiResponse sendRequestToPicupBoyOrderByOutlet(Long productOrderId);
    ApiResponse sendRequestToPicupBoyOrderByPickup(Long productOrderId);

    ApiResponse acceptOrderByPickup(Long productOrderId);
    ApiResponse cancelRequestOrderByPickup(Long productOrderId);
    ApiResponse completeOrderByPickupBoy(Long productOrderId);
    ApiResponse completeOrderByOutlet(Long productOrderId);
    ApiResponse notifyUserForDelivery(Long productOrderId);

    ProductOrderResponse getAllProductOrderByUser();
    ProductOrderResponse getAllProductOrderByOutletId(Long outletId, Pageable pageable);



}
