package com.example.lovepreet300352889.repositories;

import com.example.lovepreet300352889.entities.salesman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface salesmanRepository extends JpaRepository<salesman, Long> {
    List<salesman> findSalesmanById (long kw);
}
