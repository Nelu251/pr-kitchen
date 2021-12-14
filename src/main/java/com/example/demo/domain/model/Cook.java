package com.example.demo.domain.model;

import com.example.demo.domain.dto.PreparedOrder;
import com.example.demo.repository.Foods;
import com.example.demo.repository.Orders;
import com.example.demo.repository.PreparedOrders;
import com.example.demo.service.CookService;
import com.example.demo.service.OrderService;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
@Log4j
public class Cook implements Runnable {

    @Autowired
    OrderService orderService;

    @Autowired
    CookService cookService;

    static Semaphore stoves = new Semaphore(2);

    private int id;
    private String name;
    private int rank;
    private int proficiency;
    private String catchPhrase;

    public void prepareFood(List<Integer> list) throws InterruptedException {
        if (!list.isEmpty()) {
            Food food = Foods.getFoodById(list.get(0));
            if (food.getCookingApparatus() == null) {
                Thread.sleep(1000);
            } else {
                switch (food.getCookingApparatus()) {
                    case OVEN:
                        useOven(food.getPreparationTime());
                        break;
                    case STOVE:
                        useStove(food.getPreparationTime());
                        break;
                }
            }

            if (!PreparedOrders.hasPreparedOrder(list.get(1))) {
                PreparedOrder preparedOrder = orderService.convertFromOrderRequestToPreparedOrder(Orders.getOrderById(list.get(1)));

                orderService.savePreparedOrder(preparedOrder);
            }

            PreparedOrder preparedOrder = PreparedOrders.getPreparedOrderById(list.get(1));
            preparedOrder.getItems().add(list.get(0));
            preparedOrder.getCookingDetails().add(List.of(list.get(0)));
            preparedOrder.getCookingDetails().add(List.of(id));

            if (PreparedOrders.checkIfOrderIsDone(preparedOrder)) {
                cookService.sendPreparedOrder(preparedOrder);
            }
        }
    }

    public static void useStove(int time) throws InterruptedException {
        //        stoves.acquire(1);
        Thread.sleep(1000);
        //        stoves.release(1);
    }

    public static synchronized void useOven(int time) throws InterruptedException {
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        try {
            while (true) {
                prepareFood(Objects.requireNonNull(Orders.findFood()));
            }
        } catch (Exception e) {
            System.out.println("suiuahiauds");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
