package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import com.soft_kali.mfoodly.entity.status.OrderStatusEntity;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity,Long> {

    Page<ProductOrderEntity> findAllByOutletName(OutletEntity outletName, Pageable pageable);

    Page<ProductOrderEntity> findAllBySellerAndOrderStatusEntity(UserEntity seller, OrderStatusEntity orderStatusEntity, Pageable pageable);
    Page<ProductOrderEntity> findAllBySellerAndOrderStatusEntityAndOrderStatusEntity(UserEntity seller,
                                                                                     OrderStatusEntity orderStatusEntity,
                                                                                     OrderStatusEntity orderStatusEntity2,
                                                                                     Pageable pageable);

    Page<ProductOrderEntity> findAllBySellerAndOrderStatusEntityAndOrderStatusEntityAndOrderStatusEntity(UserEntity seller,
                                                                                     OrderStatusEntity orderStatusEntity,
                                                                                     OrderStatusEntity orderStatusEntity2,
                                                                                     OrderStatusEntity orderStatusEntity3,
                                                                                     Pageable pageable);
    Page<ProductOrderEntity> findAllBySeller(UserEntity seller, Pageable pageable);

    long countBySellerAndOrderStatusEntity(UserEntity seller, OrderStatusEntity orderStatusEntity);
    @Query("SELECT p FROM ProductOrderEntity p WHERE p.createdTime LIKE %?1% OR p.totalPrice LIKE %?1%")
    Page<ProductOrderEntity> findAll(String key, Pageable pageable);
}
