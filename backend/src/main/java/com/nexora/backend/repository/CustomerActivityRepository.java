package com.nexora.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexora.backend.entity.CustomerActivity;

public interface CustomerActivityRepository
        extends JpaRepository<CustomerActivity, Long> {

    List<CustomerActivity> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}