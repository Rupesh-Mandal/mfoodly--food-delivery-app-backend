package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OutletRepository extends JpaRepository<OutletEntity, Long> {

    Page<OutletEntity> findAllByUserEntity(UserEntity userEntity, Pageable pageable);

    long countByUserEntity(UserEntity userEntity);

    @Query("SELECT o FROM OutletEntity o WHERE o.outletName LIKE %?1% OR o.outletDescription LIKE %?1% OR o.outletSlogan LIKE %?1% OR o.outletEmail LIKE %?1%")
    Page<OutletEntity> findAll(String key, Pageable pageable);

}
