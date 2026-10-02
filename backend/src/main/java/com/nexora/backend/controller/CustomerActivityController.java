package com.nexora.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexora.backend.entity.ActivityType;
import com.nexora.backend.entity.CustomerActivity;
import com.nexora.backend.service.CustomerActivityService;

@RestController
@RequestMapping("/api/customers")
public class CustomerActivityController {

    private final CustomerActivityService customerActivityService;

    public CustomerActivityController(
            CustomerActivityService customerActivityService) {

        this.customerActivityService = customerActivityService;
    }

    @GetMapping("/{customerId}/activities")
    public List<CustomerActivityResponse> getCustomerActivities(
            @PathVariable Long customerId) {

        return customerActivityService
                .getActivitiesByCustomer(customerId)
                .stream()
                .map(CustomerActivityResponse::new)
                .toList();
    }

    @PostMapping("/{customerId}/activities")
    public ResponseEntity<CustomerActivityResponse> createCustomerActivity(
            @PathVariable Long customerId,
            @RequestParam Long performedById,
            @RequestParam ActivityType activityType,
            @RequestParam String description) {

        CustomerActivity activity =
                customerActivityService.createActivity(
                        customerId,
                        performedById,
                        activityType,
                        description
                );

        CustomerActivityResponse response =
                new CustomerActivityResponse(activity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}