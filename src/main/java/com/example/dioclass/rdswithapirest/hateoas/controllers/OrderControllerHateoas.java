package com.example.dioclass.rdswithapirest.hateoas.controllers;

import com.example.dioclass.rdswithapirest.hateoas.entities.OrderHateoas;
import com.example.dioclass.rdswithapirest.hateoas.entities.Status;
import com.example.dioclass.rdswithapirest.hateoas.exceptions.OrderNotFoundExceptionHateoas;
import com.example.dioclass.rdswithapirest.hateoas.repositories.OrderRepositoryHateoas;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderControllerHateoas {

    private final OrderRepositoryHateoas repositoryOrder;

    public OrderControllerHateoas(OrderRepositoryHateoas repositoryOrder){
        this.repositoryOrder = repositoryOrder;
    }

    @GetMapping("/orders")
    ResponseEntity<List<OrderHateoas>> consultOrderAll(){
        List<OrderHateoas> ordersList = repositoryOrder.findAll();
        long idOrder;
        if(ordersList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        for(OrderHateoas orderHateoas : ordersList){
            idOrder = orderHateoas.getId();
            Link linkUri = linkTo(methodOn(OrderControllerHateoas.class).consultOneOrder(idOrder)).withSelfRel();
            orderHateoas.add(linkUri);
            linkUri = linkTo(methodOn(OrderControllerHateoas.class).consultOrderAll()).withRel("List of Orders");
            orderHateoas.add(linkUri);
        }
        return new ResponseEntity<List<OrderHateoas>>(ordersList, HttpStatus.OK);
    }

    @GetMapping("orders/{id}")
    ResponseEntity<OrderHateoas> consultOneOrder(@PathVariable Long id){
        OrderHateoas order = repositoryOrder.findById(id).orElseThrow(()-> new OrderNotFoundExceptionHateoas(id));
        order.add(linkTo(methodOn(OrderControllerHateoas.class).consultOrderAll()).withRel("All orders"));
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @PostMapping("/orders")
    OrderHateoas newOrder(@RequestBody OrderHateoas newOrder){
        return repositoryOrder.save(newOrder);
    }

    @PutMapping("/orders/{id}")
    OrderHateoas replaceOrder(@RequestBody OrderHateoas newOrder, long id){
        return repositoryOrder.findById(id).map(order ->{
            order.setDescription(newOrder.getDescription());
            order.setStatus(newOrder.getStatus());
            return repositoryOrder.save(newOrder);
        }).orElseGet(()->{
            newOrder.setId(id);
            return repositoryOrder.save(newOrder);
        });
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable long id){
        repositoryOrder.deleteById(id);
    }

    @PutMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancelOrderById(@PathVariable long id){
        OrderHateoas cancelledOrder = repositoryOrder.findById(id).orElseThrow(
                () -> new OrderNotFoundExceptionHateoas(id));
        if(cancelledOrder.getStatus() == Status.IN_PROGRESS){
            cancelledOrder.setStatus(Status.CANCELLED);
            cancelledOrder.add(linkTo(methodOn(OrderControllerHateoas.class).consultOneOrder(id)).withSelfRel());
            cancelledOrder.add(linkTo(methodOn(OrderControllerHateoas.class).consultOrderAll()).withRel("Complete Order List"));
            repositoryOrder.save(cancelledOrder);
            return new ResponseEntity<>(cancelledOrder, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body("You can´t complete the task, the order has a "+ cancelledOrder.getStatus()+" Status");
    }

    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<?> completeOrderById(@PathVariable long id){
        OrderHateoas completedOrder = repositoryOrder.findById(id).orElseThrow(
                () -> new OrderNotFoundExceptionHateoas(id));
        if(completedOrder.getStatus() == Status.IN_PROGRESS){
            completedOrder.setStatus(Status.COMPLETED);
            completedOrder.add(linkTo(methodOn(OrderControllerHateoas.class).consultOneOrder(id)).withSelfRel());
            completedOrder.add(linkTo(methodOn(OrderControllerHateoas.class).consultOrderAll()).withRel("Complete Order List"));
            repositoryOrder.save(completedOrder);
            return new ResponseEntity<>(completedOrder, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body("You can´t complete the task, the order has a "+ completedOrder.getStatus()+" Status");
    }
}
