package com.example.demo.domain.model;

import com.example.demo.domain.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private List<Integer> itmes;
    private int priority;
    private int maxWait;
    private OrderStatus orderStatus;
    private long order_pick_up_timestamp;
    private long order_serving_timestamp;
    private long Order_total_preparing_time;
}
