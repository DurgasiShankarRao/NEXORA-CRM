package com.nexora.backend.controller;

import java.time.LocalDateTime;

import com.nexora.backend.entity.ActivityType;
import com.nexora.backend.entity.CustomerActivity;

public class CustomerActivityResponse {

    private Long id;

    private Long customerId;

    private String customerName;

    private Long performedById;

    private String performedByName;

    private ActivityType activityType;

    private String description;

    private LocalDateTime createdAt;

    public CustomerActivityResponse(CustomerActivity activity) {

        this.id = activity.getId();

        this.customerId = activity.getCustomer().getId();

        this.customerName = activity.getCustomer().getFullName();

        if (activity.getPerformedBy() != null) {
            this.performedById = activity.getPerformedBy().getId();
            this.performedByName = activity.getPerformedBy().getFullName();
        }

        this.activityType = activity.getActivityType();

        this.description = activity.getDescription();

        this.createdAt = activity.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Long getPerformedById() {
        return performedById;
    }

    public String getPerformedByName() {
        return performedByName;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}