package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class OrderResponse {

    private int order_id;
    private int table_id;
    private int waiter_id;
    private List<Integer> items;
    private int priority;
    private int max_wait;
    private long pick_up_time;
    private List<List<Integer>> cookingDetails;

}
