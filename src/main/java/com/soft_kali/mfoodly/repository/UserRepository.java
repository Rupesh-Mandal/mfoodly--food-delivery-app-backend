package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneNumber(String email);

}
