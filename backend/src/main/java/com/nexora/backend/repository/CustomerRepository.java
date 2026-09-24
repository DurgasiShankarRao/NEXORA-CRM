package com.nexora.backend.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nexora.backend.entity.Customer;
import com.nexora.backend.entity.CustomerStatus;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByFullNameContainingIgnoreCase(String fullName);

    List<Customer> findByStatus(CustomerStatus status);

    List<Customer> findByEmailContainingIgnoreCase(String email);

    List<Customer> findByPhoneContaining(String phone);

    List<Customer> findByCompanyContainingIgnoreCase(String company);

    List<Customer> findBySourceContainingIgnoreCase(String source);

    List<Customer> findByAddressContainingIgnoreCase(String address);

    List<Customer> findByNotesContainingIgnoreCase(String notes);

    List<Customer> findByAssignedToId(Long userId);

    @Query("""
        SELECT c
        FROM Customer c
        WHERE (:search IS NULL OR LOWER(c.fullName) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')))
        AND (:phone IS NULL OR c.phone LIKE CONCAT('%', :phone, '%'))
        AND (:company IS NULL OR LOWER(c.company) LIKE LOWER(CONCAT('%', :company, '%')))
        AND (:source IS NULL OR LOWER(c.source) LIKE LOWER(CONCAT('%', :source, '%')))
        AND (:address IS NULL OR LOWER(c.address) LIKE LOWER(CONCAT('%', :address, '%')))
        AND (:notes IS NULL OR LOWER(c.notes) LIKE LOWER(CONCAT('%', :notes, '%')))
        AND (:assignedTo IS NULL OR c.assignedTo.id = :assignedTo)
        AND (:status IS NULL OR c.status = :status)
        """)
    Page<Customer> searchCustomersWithFilters(

            @Param("search") String search,

            @Param("email") String email,

            @Param("phone") String phone,

            @Param("company") String company,

            @Param("source") String source,

            @Param("address") String address,

            @Param("notes") String notes,

            @Param("assignedTo") Long assignedTo,

            @Param("status") CustomerStatus status,

            Pageable pageable

    );

}