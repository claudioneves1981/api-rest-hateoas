package com.example.dioclass.rdswithapirest.myfirstapi.repository;

import com.example.dioclass.rdswithapirest.myfirstapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
