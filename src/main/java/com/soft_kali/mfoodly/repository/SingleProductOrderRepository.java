package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.product_order.SingleProductOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleProductOrderRepository extends JpaRepository<SingleProductOrderEntity,Long> {
}
