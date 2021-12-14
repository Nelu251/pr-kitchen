package com.example.demo.repository;

import com.example.demo.domain.dto.OrderRequest;
import com.example.demo.domain.model.Food;
import java.util.Collections;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class Orders {

    public static final List<OrderRequest> safeList = Collections.synchronizedList(new ArrayList<>());

    public void saveOrder(OrderRequest orderRequest) {
        safeList.add(orderRequest);
        System.out.println("Recieving new order : " + orderRequest);
    }

    public List<OrderRequest> getAll() {
        return safeList;
    }

    public static OrderRequest getOrderById(int id) {
        for (OrderRequest orderRequest : safeList) {
            if (orderRequest.getOrder_id() == id) {
                return orderRequest;
            }
        }
        return null;
    }

    public synchronized List<Food> findFoodByComplexity(int rankId, int proficiency) {
        List<Food> list = new ArrayList<>();

        for (OrderRequest order : safeList) {
            for (int j = 0; j < order.getItems().size(); j++) {
                if (order.getPriority() == rankId || order.getPriority() == rankId - 1) {
                    list.add(Foods.getFoodById(j));
                    order.getItems().remove(j);
                    order.getItems().add(-1);
                } else {
                    break;
                }
            }
            if (list.size() == proficiency) {
                break;
            }
        }

        return list;
    }

    public static synchronized List<Integer> findFood() throws NoSuchElementException {
        List<Integer> list = new ArrayList<>();

        for (OrderRequest orderRequest : safeList) {
            for (int i = 0; i < orderRequest.getItems().size(); i++) {
                if (orderRequest.getItems().get(i) == -1) {
                } else {
                    log.info("found food with id : {}", orderRequest.getItems().get(i));
                    list.add(orderRequest.getItems().get(i));
                    list.add(orderRequest.getOrder_id());
                    orderRequest.getItems().set(i, -1);
                    return list;
                }
            }
        }

        log.warn("this thread can't find any foods left : {}", Thread.currentThread());
        return null;
    }

    public static synchronized boolean noMoreFoods() {
        boolean flag = false;

        for (OrderRequest orderRequest : safeList) {
            for (Integer item : orderRequest.getItems()) {
                if (item == -1) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public static boolean isOrderInPending(int orderId) {
        boolean flag = false;

        for (Integer foodNr : safeList.stream().filter(orderRequest -> orderRequest.getOrder_id() == orderId).findFirst().get().getItems()) {
            if (foodNr == -1) {
                flag = true;
            }
        }

        return flag;
    }
}