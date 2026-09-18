package com.nexora.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nexora.backend.entity.Role;
import com.nexora.backend.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}