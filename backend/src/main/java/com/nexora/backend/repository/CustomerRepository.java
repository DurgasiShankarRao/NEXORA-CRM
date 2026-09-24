package com.nexora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexora.backend.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}