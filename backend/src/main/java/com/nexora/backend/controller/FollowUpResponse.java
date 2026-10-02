package com.nexora.backend.controller;

import java.time.LocalDateTime;

import com.nexora.backend.entity.FollowUp;
import com.nexora.backend.entity.FollowUpStatus;

public class FollowUpResponse {

    private Long id;

    private Long customerId;

    private String customerName;

    private Long assignedToId;

    private String assignedToName;

    private LocalDateTime followUpDate;

    private FollowUpStatus status;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public FollowUpResponse(FollowUp followUp) {

        this.id = followUp.getId();

        this.customerId = followUp.getCustomer().getId();

        this.customerName = followUp.getCustomer().getFullName();

        this.assignedToId = followUp.getAssignedTo().getId();

        this.assignedToName = followUp.getAssignedTo().getFullName();

        this.followUpDate = followUp.getFollowUpDate();

        this.status = followUp.getStatus();

        this.notes = followUp.getNotes();

        this.createdAt = followUp.getCreatedAt();

        this.updatedAt = followUp.getUpdatedAt();
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

    public Long getAssignedToId() {
        return assignedToId;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public LocalDateTime getFollowUpDate() {
        return followUpDate;
    }

    public FollowUpStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}