package com.example.demo.domain.model;

import com.example.demo.domain.dto.PreparedOrder;
import com.example.demo.repository.Foods;
import com.example.demo.repository.Orders;
import com.example.demo.repository.PreparedOrders;
import com.example.demo.service.CookService;
import com.example.demo.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
@Slf4j
public class Cook implements Runnable {

    @Autowired
    OrderService orderService;

    @Autowired
    CookService cookService;

    static Semaphore stoves = new Semaphore(4);
    static Semaphore ovens = new Semaphore(4);

    private int id;
    private String name;
    private int rank;
    private int proficiency;
    private String catchPhrase;

    public void prepareFood(List<Integer> list) throws InterruptedException {
        if (!(list == null)) {
            Food food = Foods.getFoodById(list.get(0));
            if (food.getCookingApparatus() == null) {
                Thread.sleep(food.getPreparationTime() * 100);
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
            synchronized (Cook.class) {
                if (!PreparedOrders.hasPreparedOrder(list.get(1))) {
                    log.info("Didn't find a Prepared Order with id : {}", list.get(1));
                    PreparedOrder preparedOrder = orderService.convertFromOrderRequestToPreparedOrder(Orders.getOrderById(list.get(1)));

                    log.info("converted PreparedOrder : {}", preparedOrder);
                    orderService.savePreparedOrder(preparedOrder);
                }
            }

            PreparedOrder preparedOrder = PreparedOrders.getPreparedOrderById(list.get(1));
            preparedOrder.getItems().add(list.get(0));
            List<Integer> details = new ArrayList<>(2);
            details.add(list.get(0));
            details.add(id);
            preparedOrder.getCookingDetails().add(details);

            if (PreparedOrders.checkIfOrderIsDone(preparedOrder)) {
                preparedOrder.setCookingTime((int) (System.currentTimeMillis() / 1000L - preparedOrder.getPickUpTime()));
                cookService.sendPreparedOrder(preparedOrder);
            }
        }
        log.warn("This cook didn't get a food to prepare : {}", this.id);
    }

    public static void useStove(int time) throws InterruptedException {
        stoves.acquire(1);
        Thread.sleep(time * 200);
        stoves.release(1);
    }

    public static void useOven(int time) throws InterruptedException {
        stoves.acquire(1);
        Thread.sleep(time * 200);
        stoves.release(1);
    }

    @Override
    public void run() {
        try {
            while (true) {
                prepareFood(Objects.requireNonNull(Orders.findFood()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
