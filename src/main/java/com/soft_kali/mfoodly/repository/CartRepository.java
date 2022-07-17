package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import com.soft_kali.mfoodly.entity.cart.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserEntityAndOutletEntity(UserEntity userEntity, OutletEntity outletEntity);

    List<CartEntity> findAllByUserEntity(UserEntity userEntity);

    List<CartEntity> findAllByUserEntityAndOutletEntity(UserEntity userEntity, OutletEntity outletEntity);

    void deleteAllByUserEntity(UserEntity userEntity);
}
