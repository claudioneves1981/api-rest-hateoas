package com.example.dioclass.rdswithapirest.hateoas.exceptions;

public class EmployeeNotFoundExceptionHateoas extends RuntimeException{
    public EmployeeNotFoundExceptionHateoas(Long id){
        super("Coud not find the id:"+ id);
    }
}
