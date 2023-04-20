package com.example.dioclass.rdswithapirest.apirest.exception;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Long id){
        super("Coud not find the id:"+ id);
    }
}
