package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.product.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    @Query("SELECT c FROM CategoryEntity c WHERE c.categoryTitle LIKE %?1% OR c.categoryDescription LIKE %?1%")
    Page<CategoryEntity> findAll(String key, Pageable pageable);
}
