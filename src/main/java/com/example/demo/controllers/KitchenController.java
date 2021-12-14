package com.example.demo.controllers;

import com.example.demo.domain.dto.OrderRequest;

import com.example.demo.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class KitchenController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public String receiveOrderRequest(@RequestBody OrderRequest orderRequest) {
        orderService.saveOrders(orderRequest);
        return "ok";
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrderRequest>> show() {
        return ResponseEntity.ok(orderService.show());
    }
}
