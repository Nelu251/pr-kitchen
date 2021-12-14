package com.example.demo.domain.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {

    private int order_id;
    private int table_id;
    private int waiter_id;
    private List<Integer> items;
    private int priority;
    private int max_wait;
    private long pick_up_time;
}
