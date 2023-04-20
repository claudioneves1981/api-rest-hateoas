package com.example.dioclass.rdswithapirest.hateoas.exceptions;

public class OrderNotFoundExceptionHateoas extends RuntimeException{

    public OrderNotFoundExceptionHateoas(Long id){
        super("Could not find the id:"+ id);
    }

}
