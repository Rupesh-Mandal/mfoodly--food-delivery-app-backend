package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.OutletEntity;
import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity,Long> {

    Page<ProductOrderEntity> findAllByOutletName(OutletEntity outletName, Pageable pageable);

}
