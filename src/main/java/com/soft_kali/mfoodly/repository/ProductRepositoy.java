package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import com.soft_kali.mfoodly.entity.user.OutletEntity;
import com.soft_kali.mfoodly.entity.product.ProductEntity;
import com.soft_kali.mfoodly.entity.location.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoy extends JpaRepository<ProductEntity,Long> {


    Page<ProductEntity> findAllByCityEntity(CityEntity cityEntity,Pageable pageable);
    Page<ProductEntity> findAllByOutletName(OutletEntity outletName,Pageable pageable);

    Page<ProductEntity> findAllByCityEntityAndCategoryEntitiesIsLike(CityEntity cityEntity,CategoryEntity categoryEntity, Pageable pageable);


}
