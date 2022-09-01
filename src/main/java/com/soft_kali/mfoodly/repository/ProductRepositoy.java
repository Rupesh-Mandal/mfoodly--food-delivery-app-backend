package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.product.ProductEntity;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepositoy extends JpaRepository<ProductEntity,Long> {


    Page<ProductEntity> findAllByCityEntity(CityEntity cityEntity,Pageable pageable);
    Page<ProductEntity> findAllByOutletName(OutletEntity outletName,Pageable pageable);

    Page<ProductEntity> findAllByCityEntityAndCategoryEntitiesIsLike(CityEntity cityEntity,CategoryEntity categoryEntity, Pageable pageable);

    long countByUserEntity(UserEntity userEntity);
    @Query("SELECT p FROM ProductEntity p WHERE p.productName LIKE %?1% OR p.productDescription LIKE %?1%")
    Page<ProductEntity> findAll(String key,Pageable pageable);


}
