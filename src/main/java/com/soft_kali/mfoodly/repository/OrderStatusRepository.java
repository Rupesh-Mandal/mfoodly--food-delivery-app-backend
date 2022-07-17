package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.status.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity,Integer> {
}
