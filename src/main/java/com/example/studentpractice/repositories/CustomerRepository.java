package com.example.studentpractice.repositories;

import com.example.studentpractice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findCustomerById (long kw);
}
