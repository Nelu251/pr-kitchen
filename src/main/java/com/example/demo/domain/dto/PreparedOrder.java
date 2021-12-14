package com.example.demo.domain.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class PreparedOrder {

    private int orderId;
    private int tableId;
    private int waiterId;
    @Builder.Default
    private List<Integer> items = new ArrayList<>();
    private int priority;
    private int maxWait;
    private long pickUpTime;
    private int cookingTime;
    @Builder.Default
    private List<List<Integer>> cookingDetails = new ArrayList<>();

}
