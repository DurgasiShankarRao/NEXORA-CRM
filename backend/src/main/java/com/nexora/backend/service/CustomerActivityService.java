package com.nexora.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nexora.backend.entity.ActivityType;
import com.nexora.backend.entity.Customer;
import com.nexora.backend.entity.CustomerActivity;
import com.nexora.backend.entity.User;
import com.nexora.backend.repository.CustomerActivityRepository;
import com.nexora.backend.repository.CustomerRepository;
import com.nexora.backend.repository.UserRepository;

@Service
public class CustomerActivityService {

    private final CustomerActivityRepository customerActivityRepository;

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    public CustomerActivityService(
            CustomerActivityRepository customerActivityRepository,
            CustomerRepository customerRepository,
            UserRepository userRepository) {

        this.customerActivityRepository = customerActivityRepository;

        this.customerRepository = customerRepository;

        this.userRepository = userRepository;
    }

    public List<CustomerActivity> getActivitiesByCustomer(Long customerId) {

        return customerActivityRepository
                .findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    public CustomerActivity createActivity(
            Long customerId,
            Long performedById,
            ActivityType activityType,
            String description) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        User performedBy = null;

        if (performedById != null) {

            performedBy = userRepository.findById(performedById)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));
        }

        CustomerActivity activity = new CustomerActivity();

        activity.setCustomer(customer);
        activity.setPerformedBy(performedBy);
        activity.setActivityType(activityType);
        activity.setDescription(description);
        activity.setCreatedAt(LocalDateTime.now());

        return customerActivityRepository.save(activity);
    }
}