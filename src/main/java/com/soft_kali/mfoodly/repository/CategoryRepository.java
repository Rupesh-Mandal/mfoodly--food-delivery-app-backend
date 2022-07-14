package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
}
