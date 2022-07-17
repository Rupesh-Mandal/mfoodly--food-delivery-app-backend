package com.soft_kali.mfoodly.repository;

import com.soft_kali.mfoodly.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
