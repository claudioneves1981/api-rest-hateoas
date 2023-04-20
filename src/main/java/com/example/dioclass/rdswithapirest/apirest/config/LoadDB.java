package com.example.dioclass.rdswithapirest.apirest.config;

import com.example.dioclass.rdswithapirest.apirest.entity.Employee;
import com.example.dioclass.rdswithapirest.apirest.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class LoadDB {
    private static final Logger log = LoggerFactory.getLogger(LoadDB.class);

    @Bean
    CommandLineRunner applicationRunner(EmployeeRepository employeeRepository){
        return args -> {
            log.info("Log of event save user 1: "+ employeeRepository.save(new Employee("Claudio","ADMIN","Rua jupi 215")));
            log.info("Log of event save user 1: "+ employeeRepository.save(new Employee("Clovis","USERS","Avenida Joao Dias, 3000")));
        };
    }
}
