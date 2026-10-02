package com.nexora.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nexora.backend.entity.Customer;
import com.nexora.backend.entity.FollowUp;
import com.nexora.backend.entity.FollowUpStatus;
import com.nexora.backend.entity.User;
import com.nexora.backend.repository.CustomerRepository;
import com.nexora.backend.repository.FollowUpRepository;
import com.nexora.backend.repository.UserRepository;

@Service
public class FollowUpService {

    private final FollowUpRepository followUpRepository;

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    public FollowUpService(
            FollowUpRepository followUpRepository,
            CustomerRepository customerRepository,
            UserRepository userRepository) {

        this.followUpRepository = followUpRepository;

        this.customerRepository = customerRepository;

        this.userRepository = userRepository;
    }

    public List<FollowUp> getFollowUpsByCustomer(Long customerId) {

        return followUpRepository
                .findByCustomerIdOrderByFollowUpDateAsc(customerId);
    }

    public List<FollowUp> getFollowUpsByUser(Long userId) {

        return followUpRepository
                .findByAssignedToIdOrderByFollowUpDateAsc(userId);
    }

    public List<FollowUp> getFollowUpsByStatus(FollowUpStatus status) {

        return followUpRepository
                .findByStatusOrderByFollowUpDateAsc(status);
    }

    public FollowUp createFollowUp(
            Long customerId,
            Long assignedToId,
            LocalDateTime followUpDate,
            String notes) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        User assignedTo = userRepository.findById(assignedToId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        FollowUp followUp = new FollowUp();

        followUp.setCustomer(customer);

        followUp.setAssignedTo(assignedTo);

        followUp.setFollowUpDate(followUpDate);

        followUp.setStatus(FollowUpStatus.PENDING);

        followUp.setNotes(notes);

        followUp.setCreatedAt(LocalDateTime.now());

        followUp.setUpdatedAt(LocalDateTime.now());

        return followUpRepository.save(followUp);
    }

    public FollowUp completeFollowUp(Long followUpId) {

        FollowUp followUp = followUpRepository.findById(followUpId)
                .orElseThrow(() ->
                        new RuntimeException("Follow-up not found"));

        followUp.setStatus(FollowUpStatus.COMPLETED);

        followUp.setUpdatedAt(LocalDateTime.now());

        return followUpRepository.save(followUp);
    }

    public FollowUp cancelFollowUp(Long followUpId) {

        FollowUp followUp = followUpRepository.findById(followUpId)
                .orElseThrow(() ->
                        new RuntimeException("Follow-up not found"));

        followUp.setStatus(FollowUpStatus.CANCELLED);

        followUp.setUpdatedAt(LocalDateTime.now());

        return followUpRepository.save(followUp);
    }
}