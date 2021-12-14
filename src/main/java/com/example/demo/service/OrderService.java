package com.example.demo.service;

import com.example.demo.domain.dto.OrderRequest;
import com.example.demo.domain.dto.PreparedOrder;
import com.example.demo.repository.Orders;
import com.example.demo.repository.PreparedOrders;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Log4j
public class OrderService {
    @Autowired
    CookService cookService;

    @Autowired
    private Orders ordersRequests;

    @Autowired
    private PreparedOrders preparedOrders;

    public void saveOrders(OrderRequest orderRequest){
        ordersRequests.saveOrder(orderRequest);
        cookService.letsGo();
    }

    public synchronized void savePreparedOrder(PreparedOrder preparedOrder) {
        preparedOrders.savePreparedOrder(preparedOrder);
    }

    public List<OrderRequest> show() {
        return ordersRequests.getAll();
    }

    public List<PreparedOrder> showPrepared() {
        return preparedOrders.getAll();
    }

    public PreparedOrder convertFromOrderRequestToPreparedOrder(OrderRequest orderRequest) {
        return PreparedOrder.builder()
            .orderId(orderRequest.getOrder_id())
            .maxWait(orderRequest.getMax_wait())
            .priority(orderRequest.getPriority())
            .tableId(orderRequest.getTable_id())
            .waiterId(orderRequest.getWaiter_id())
            .pickUpTime((int)orderRequest.getPick_up_time())
            .build();
    }



}
