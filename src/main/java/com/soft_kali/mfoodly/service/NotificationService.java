package com.soft_kali.mfoodly.service;

import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;

public interface NotificationService {
    void orderCancelledBySeller(ProductOrderEntity productOrderEntity);

    void orderAcceptedBySeller(ProductOrderEntity productOrderEntity);
}
