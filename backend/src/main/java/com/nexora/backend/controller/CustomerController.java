package com.nexora.backend.controller;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nexora.backend.entity.Customer;
import com.nexora.backend.entity.CustomerStatus;
import com.nexora.backend.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "fullName",
            "email",
            "phone",
            "company",
            "status",
            "source",
            "address",
            "notes",
            "createdAt",
            "updatedAt"
    );

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Page<Customer> getAllCustomers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) Long assignedTo,
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid sort field: " + sortBy
            );
        }

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return customerService.searchCustomersWithFilters(
                search,
                email,
                phone,
                company,
                source,
                address,
                notes,
                assignedTo,
                status,
                pageable
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody Customer customer) {

        return customerService.getCustomerById(id)
                .map(existingCustomer -> {

                    existingCustomer.setFullName(customer.getFullName());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setPhone(customer.getPhone());
                    existingCustomer.setCompany(customer.getCompany());
                    existingCustomer.setStatus(customer.getStatus());
                    existingCustomer.setSource(customer.getSource());
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setNotes(customer.getNotes());
                    existingCustomer.setAssignedTo(customer.getAssignedTo());

                    return ResponseEntity.ok(
                            customerService.saveCustomer(existingCustomer)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {

        if (customerService.getCustomerById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}