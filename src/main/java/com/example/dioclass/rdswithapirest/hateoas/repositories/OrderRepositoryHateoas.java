package com.example.dioclass.rdswithapirest.hateoas.repositories;

import com.example.dioclass.rdswithapirest.hateoas.entities.OrderHateoas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryHateoas extends JpaRepository<OrderHateoas, Long> {
}
