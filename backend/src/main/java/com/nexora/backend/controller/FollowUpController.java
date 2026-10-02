package com.nexora.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexora.backend.entity.FollowUp;
import com.nexora.backend.entity.FollowUpStatus;
import com.nexora.backend.service.FollowUpService;

@RestController
@RequestMapping("/api/follow-ups")
public class FollowUpController {

    private final FollowUpService followUpService;

    public FollowUpController(FollowUpService followUpService) {

        this.followUpService = followUpService;

    }

    @GetMapping("/customer/{customerId}")
    public List<FollowUpResponse> getFollowUpsByCustomer(
            @PathVariable Long customerId) {

        return followUpService
                .getFollowUpsByCustomer(customerId)
                .stream()
                .map(FollowUpResponse::new)
                .toList();

    }

    @GetMapping("/user/{userId}")
    public List<FollowUpResponse> getFollowUpsByUser(
            @PathVariable Long userId) {

        return followUpService
                .getFollowUpsByUser(userId)
                .stream()
                .map(FollowUpResponse::new)
                .toList();

    }

    @GetMapping("/status/{status}")
    public List<FollowUpResponse> getFollowUpsByStatus(
            @PathVariable FollowUpStatus status) {

        return followUpService
                .getFollowUpsByStatus(status)
                .stream()
                .map(FollowUpResponse::new)
                .toList();

    }

    @PostMapping
    public ResponseEntity<FollowUpResponse> createFollowUp(
            @RequestParam Long customerId,
            @RequestParam Long assignedToId,
            @RequestParam LocalDateTime followUpDate,
            @RequestParam String notes) {

        FollowUp followUp =
                followUpService.createFollowUp(
                        customerId,
                        assignedToId,
                        followUpDate,
                        notes
                );

        FollowUpResponse response =
                new FollowUpResponse(followUp);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @PutMapping("/{followUpId}/complete")
    public ResponseEntity<FollowUpResponse> completeFollowUp(
            @PathVariable Long followUpId) {

        FollowUp followUp =
                followUpService.completeFollowUp(followUpId);

        FollowUpResponse response =
                new FollowUpResponse(followUp);

        return ResponseEntity
                .ok(response);
    }

    @PutMapping("/{followUpId}/cancel")
    public ResponseEntity<FollowUpResponse> cancelFollowUp(
            @PathVariable Long followUpId) {

        FollowUp followUp =
                followUpService.cancelFollowUp(followUpId);

        FollowUpResponse response =
                new FollowUpResponse(followUp);

        return ResponseEntity
                .ok(response);
    }
}