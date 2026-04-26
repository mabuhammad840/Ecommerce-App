package com.engkhaleel.full_project_spring_react.Controller;

import com.engkhaleel.full_project_spring_react.Model.DTO.OrderRequest;
import com.engkhaleel.full_project_spring_react.Model.DTO.OrderResponse;
import com.engkhaleel.full_project_spring_react.Model.Order;
import com.engkhaleel.full_project_spring_react.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;




    // from clint side to server side .
    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    // to fetch all orders
    // return orders from database to the clint side
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrderResponses() {
        List<OrderResponse> responses = orderService.getAllOrderResponses();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
