package com.example.demo.utils;

import com.example.demo.domain.dto.OrderRequest;
import java.util.Comparator;

public class OrderComparator implements Comparator<OrderRequest> {

    @Override
    public int compare(OrderRequest o1, OrderRequest o2) {
        int c;
        c = o2.getPriority() - o1.getPriority();
        if (c == 0) {
            c = o1.getPick_up_time() - o2.getPick_up_time();
        }
        return c;
    }
}
