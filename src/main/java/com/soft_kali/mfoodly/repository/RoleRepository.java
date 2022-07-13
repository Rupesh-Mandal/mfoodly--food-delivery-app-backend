package com.rupesh_mandal.blog_app_backend.repository;

import com.rupesh_mandal.blog_app_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
