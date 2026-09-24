package com.nexora.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nexora.backend.entity.Customer;
import com.nexora.backend.entity.User;
import com.nexora.backend.repository.CustomerRepository;
import com.nexora.backend.repository.UserRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository,
                           UserRepository userRepository) {

        this.customerRepository = customerRepository;

        this.userRepository = userRepository;
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {

        return customerRepository.findById(id);
    }

    public List<Customer> searchCustomers(String search) {

        return customerRepository.findByFullNameContainingIgnoreCase(search);
    }

    public Customer saveCustomer(Customer customer) {

        if (customer.getAssignedTo() != null &&
            customer.getAssignedTo().getId() != null) {

            Long userId = customer.getAssignedTo().getId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new RuntimeException("Assigned user not found"));

            customer.setAssignedTo(user);
        }

        LocalDateTime now = LocalDateTime.now();

        if (customer.getCreatedAt() == null) {

            customer.setCreatedAt(now);
        }

        customer.setUpdatedAt(now);

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {

        customerRepository.deleteById(id);
    }

}