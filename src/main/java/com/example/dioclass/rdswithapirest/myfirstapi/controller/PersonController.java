package com.example.dioclass.rdswithapirest.myfirstapi.controller;

import com.example.dioclass.rdswithapirest.myfirstapi.entity.Person;
import com.example.dioclass.rdswithapirest.myfirstapi.repository.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/")
    public String helloWorld(){
        return "This is my first api in Spring Boot";
    }

    @GetMapping("/persons")
    public List<Person> consultAllPersons(){
        return personRepository.findAll();
    }

    @GetMapping("/persons/{id}")
    public Optional<Person> consultById(@PathVariable Long id){
        return personRepository.findById(id);
    }
}
