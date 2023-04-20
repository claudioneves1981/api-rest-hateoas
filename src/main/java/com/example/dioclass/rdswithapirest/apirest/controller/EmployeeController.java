package com.example.dioclass.rdswithapirest.apirest.controller;

import com.example.dioclass.rdswithapirest.apirest.entity.Employee;
import com.example.dioclass.rdswithapirest.apirest.exception.EmployeeNotFoundException;
import com.example.dioclass.rdswithapirest.apirest.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    @GetMapping("/employees")
    public  List<Employee> listOfEmployeeAll(){
        return repository.findAll();
    }

    @GetMapping("/employee/{id}")
    public Employee consultByID(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PostMapping("/employees")
    public Employee newEmployee(@RequestBody Employee newEmployee){
        return repository.save(newEmployee);
    }

    @PutMapping("/employees/{id}")
    public Employee replaceEmployee(@RequestBody Employee newEmployee, Long id){
        return repository.findById(id).map(employee ->{
            employee.setName(newEmployee.getName());
            employee.setAddress(newEmployee.getAddress());
            employee.setRole(newEmployee.getRole());
            return repository.save(newEmployee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }

    @DeleteMapping("/employee/{id}")
    void deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
    }
}
