package com.example.dioclass.rdswithapirest.myfirstapi;

import com.example.dioclass.rdswithapirest.myfirstapi.entity.Person;
import com.example.dioclass.rdswithapirest.myfirstapi.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApirestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApirestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PersonRepository repository){
        return args ->{
            repository.save(new Person("Joao", "Silva"));
            repository.save(new Person("Juliana", "Mascarenhas"));
        };
    }
}
