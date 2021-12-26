package com.example.demo.repository;

import com.example.demo.domain.dto.OrderRequest;
import com.example.demo.domain.dto.PreparedOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class PreparedOrders {

    public static final List<PreparedOrder> preparedOrdersList = Collections.synchronizedList(new ArrayList<>());

    public void savePreparedOrder(PreparedOrder preparedOrder) {
        preparedOrdersList.add(preparedOrder);
    }

    public static synchronized boolean hasPreparedOrder(int id) {
        for (PreparedOrder preparedOrder : preparedOrdersList) {
            if (preparedOrder.getOrderId() == id) {
                return true;
            }
        }
        return false;
    }

    public static PreparedOrder getPreparedOrderById(int id) {
        return preparedOrdersList.stream().filter(preparedOrder -> preparedOrder.getOrderId() == id).findFirst().orElse(null);
    }

    public static boolean checkIfOrderIsDone(PreparedOrder preparedOrder) {
        OrderRequest orderRequest = Orders.getOrderById(preparedOrder.getOrderId());
        if (preparedOrder.getItems().size() == orderRequest.getItems().size() && Orders.isOrderInPending(orderRequest.getOrder_id())) {
            return true;
        }
        return false;
    }

    public static void removePreparedOrder(int id) {
        for (int i = 0; i< preparedOrdersList.size(); i++) {
            if (preparedOrdersList.get(i).getOrderId() == id){
                preparedOrdersList.remove(i);
                break;
            }
        }
    }

    public List<PreparedOrder> getAll() {
        return preparedOrdersList;
    }

}
