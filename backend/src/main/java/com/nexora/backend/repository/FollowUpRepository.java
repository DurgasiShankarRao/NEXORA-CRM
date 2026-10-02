package com.nexora.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexora.backend.entity.FollowUp;
import com.nexora.backend.entity.FollowUpStatus;

public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {

    List<FollowUp> findByCustomerIdOrderByFollowUpDateAsc(Long customerId);

    List<FollowUp> findByAssignedToIdOrderByFollowUpDateAsc(Long userId);

    List<FollowUp> findByStatusOrderByFollowUpDateAsc(FollowUpStatus status);
}