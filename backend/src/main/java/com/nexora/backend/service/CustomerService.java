package com.nexora.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nexora.backend.entity.Customer;
import com.nexora.backend.entity.CustomerStatus;
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

    public List<Customer> searchCustomersByEmail(String email) {

        return customerRepository.findByEmailContainingIgnoreCase(email);

    }

    public List<Customer> searchCustomersByPhone(String phone) {

        return customerRepository.findByPhoneContaining(phone);

    }

    public List<Customer> searchCustomersByCompany(String company) {

        return customerRepository.findByCompanyContainingIgnoreCase(company);

    }

    public List<Customer> searchCustomersBySource(String source) {

        return customerRepository.findBySourceContainingIgnoreCase(source);

    }

    public List<Customer> searchCustomersByAddress(String address) {

        return customerRepository.findByAddressContainingIgnoreCase(address);

    }

    public List<Customer> searchCustomersByNotes(String notes) {

        return customerRepository.findByNotesContainingIgnoreCase(notes);

    }

    public List<Customer> getCustomersByAssignedUser(Long userId) {

        return customerRepository.findByAssignedToId(userId);

    }

    public List<Customer> getCustomersByStatus(CustomerStatus status) {

        return customerRepository.findByStatus(status);

    }

    public Page<Customer> searchCustomersWithFilters(

            String search,

            String email,

            String phone,

            String company,

            String source,

            String address,

            String notes,

            Long assignedTo,

            CustomerStatus status,

            Pageable pageable) {

        return customerRepository.searchCustomersWithFilters(

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