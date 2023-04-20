package com.example.dioclass.rdswithapirest.hateoas.controllers;

import com.example.dioclass.rdswithapirest.hateoas.entities.EmployeeHateoas;
import com.example.dioclass.rdswithapirest.hateoas.exceptions.EmployeeNotFoundExceptionHateoas;
import com.example.dioclass.rdswithapirest.hateoas.repositories.EmployeeRepositoryHateoas;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeControllerHateoas {

    private final EmployeeRepositoryHateoas repositoryEmployee;

    public EmployeeControllerHateoas(EmployeeRepositoryHateoas repositoryEmployee){
        this.repositoryEmployee = repositoryEmployee;
    }

    @GetMapping("/employee")
    public  ResponseEntity<List<EmployeeHateoas>> listOfEmployeeAll(){
        List<EmployeeHateoas> employeesList = repositoryEmployee.findAll();
        long idEmployee;
        if(employeesList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        for(EmployeeHateoas employeeHateoas : employeesList){
            idEmployee = employeeHateoas.getId();
            Link linkUri = linkTo(methodOn(EmployeeControllerHateoas.class).consultByID(idEmployee)).withSelfRel();
            employeeHateoas.add(linkUri);
            linkUri = linkTo(methodOn(EmployeeControllerHateoas.class).listOfEmployeeAll()).withRel("List of Employees");
            employeeHateoas.add(linkUri);
        }
        return new ResponseEntity<List<EmployeeHateoas>>(employeesList, HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeHateoas> consultByID(@PathVariable Long id){
        EmployeeHateoas employee = repositoryEmployee.findById(id).orElseThrow(() -> new EmployeeNotFoundExceptionHateoas(id));
            employee.add(linkTo(methodOn(EmployeeControllerHateoas.class).listOfEmployeeAll()).withRel("All employees"));
            return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public EmployeeHateoas newEmployee(@RequestBody EmployeeHateoas newEmployee){
        return repositoryEmployee.save(newEmployee);
    }

    @PutMapping("/employee/{id}")
    public EmployeeHateoas replaceEmployee(@RequestBody EmployeeHateoas newEmployee, Long id){
        return repositoryEmployee.findById(id).map(employees -> {
            employees.setName(newEmployee.getName());
            employees.setAddress(newEmployee.getAddress());
            employees.setRole(newEmployee.getRole());
            return repositoryEmployee.save(newEmployee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repositoryEmployee.save(newEmployee);
        });
    }

    @DeleteMapping("/employee/{id}")
    void deleteEmployee(@PathVariable Long id){
        repositoryEmployee.deleteById(id);
    }
}
