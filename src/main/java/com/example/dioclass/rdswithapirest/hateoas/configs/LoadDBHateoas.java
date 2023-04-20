package com.example.dioclass.rdswithapirest.hateoas.configs;

import com.example.dioclass.rdswithapirest.hateoas.entities.EmployeeHateoas;
import com.example.dioclass.rdswithapirest.hateoas.entities.OrderHateoas;
import com.example.dioclass.rdswithapirest.hateoas.entities.Status;
import com.example.dioclass.rdswithapirest.hateoas.repositories.EmployeeRepositoryHateoas;
import com.example.dioclass.rdswithapirest.hateoas.repositories.OrderRepositoryHateoas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDBHateoas {

    private static final Logger log = LoggerFactory.getLogger(LoadDBHateoas.class);

    @Bean
    CommandLineRunner loadEmployees(EmployeeRepositoryHateoas employeeRepositoryHateoas){
        return args -> {
            log.info("Log of event save user 1: "+ employeeRepositoryHateoas.save(new EmployeeHateoas("Claudio","ADMIN","Rua jupi 215")));
            log.info("Log of event save user 1: "+ employeeRepositoryHateoas.save(new EmployeeHateoas("Clovis","USERS","Avenida Joao Dias, 3000")));
        };
    }

    @Bean
    CommandLineRunner loadOrder(OrderRepositoryHateoas orderRepository){
        return args -> {
            orderRepository.save(new OrderHateoas(Status.COMPLETED, "Completo"));
            orderRepository.save(new OrderHateoas(Status.IN_PROGRESS, "Em Progresso"));
            orderRepository.save(new OrderHateoas(Status.IN_PROGRESS, "Em Progresso"));
            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded" + order);
            });
        };
    }
}
