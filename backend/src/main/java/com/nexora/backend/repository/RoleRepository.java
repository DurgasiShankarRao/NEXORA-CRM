package com.nexora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexora.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}