package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.cart.SubCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCartRepository extends JpaRepository<SubCartEntity,Long> {
}
