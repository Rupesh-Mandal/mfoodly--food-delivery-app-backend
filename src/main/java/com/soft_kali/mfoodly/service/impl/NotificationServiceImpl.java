package com.soft_kali.mfoodly.service.impl;

import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void orderCancelledBySeller(ProductOrderEntity productOrderEntity) {

    }

    @Override
    public void orderAcceptedBySeller(ProductOrderEntity productOrderEntity) {

    }
}
