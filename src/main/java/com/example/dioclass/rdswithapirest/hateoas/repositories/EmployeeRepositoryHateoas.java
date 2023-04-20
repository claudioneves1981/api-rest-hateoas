package com.example.dioclass.rdswithapirest.hateoas.repositories;

import com.example.dioclass.rdswithapirest.apirest.entity.Employee;
import com.example.dioclass.rdswithapirest.hateoas.entities.EmployeeHateoas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositoryHateoas extends JpaRepository<EmployeeHateoas, Long> {
}
