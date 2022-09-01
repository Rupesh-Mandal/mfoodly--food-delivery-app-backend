package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.product_order.ProductOrderEntity;
import com.soft_kali.mfoodly.entity.role.Role;
import com.soft_kali.mfoodly.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    long countAllByRoles(Role role);

    Page<UserEntity> findAllByRoles(Role role, Pageable pageable);

    Page<UserEntity> findAllByNameContainingAndRoles(String name,Role role, Pageable pageable);

}
