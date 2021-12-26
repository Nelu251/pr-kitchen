package com.example.demo.domain.model;

import static com.example.demo.domain.model.enums.CookingApparatus.OVEN;

import com.example.demo.domain.dto.Details;
import com.example.demo.domain.dto.PreparedOrder;
import com.example.demo.domain.model.enums.CookingApparatus;
import com.example.demo.repository.Foods;
import com.example.demo.repository.Orders;
import com.example.demo.repository.PreparedOrders;
import com.example.demo.service.CookService;
import com.example.demo.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
@Scope("prototype")
public class CookFood implements Runnable {

    private int id;
    private int cookId;
    private int cookRank;
    private Food food;

    @Autowired
    CookService cookService;

    @Autowired
    OrderService orderService;

    static Semaphore stoves = new Semaphore(2);
    static Semaphore ovens = new Semaphore(2);

    public static boolean stoveIsOcupated() {
        return stoves.availablePermits() == 0;
    }

    public static boolean ovenIsOcupated() {
        return ovens.availablePermits() == 0;
    }

    public void prepareFood(List<Integer> list) throws InterruptedException {
        if (list != null) {
            Food food = Foods.getFoodById(list.get(0));
            if (food.getCookingApparatus() == null) {
                Thread.sleep(food.getPreparationTime() * 1000L);
            } else {
                switch (food.getCookingApparatus()) {
                    case STOVE:
                        useStove(food.getPreparationTime());
                        break;

                    case OVEN:
                        useOven(food.getPreparationTime());
                        break;
                }
            }

            if (!PreparedOrders.hasPreparedOrder(list.get(1))) {
                    log.info("Didn't find a Prepared Order with id : {}", list.get(1));
                    PreparedOrder preparedOrder = orderService.convertFromOrderRequestToPreparedOrder(Orders.getOrderById(list.get(1)));

                    log.info("converted PreparedOrder : {}", preparedOrder);
                    orderService.savePreparedOrder(preparedOrder);
            }

            PreparedOrder preparedOrder = PreparedOrders.getPreparedOrderById(list.get(1));
            preparedOrder.getItems().add(list.get(0));
            Details details = new Details();
            details.setFoodId(list.get(0));
            details.setCookId(this.cookId);
            preparedOrder.getCookingDetails().add(details);

            if (PreparedOrders.checkIfOrderIsDone(preparedOrder)) {
                preparedOrder.setCookingTime((int) (System.currentTimeMillis() / 1000L - preparedOrder.getPickUpTime()));
                cookService.sendPreparedOrder(preparedOrder);
            }
        } else {
            log.warn("This cook didn't get a food to prepare : {}", this.id);
        }
    }

    public static void useStove(int time) throws InterruptedException {
        stoves.acquire(1);
        Thread.sleep(time * 1000L);
        stoves.release(1);
    }

    public static void useOven(int time) throws InterruptedException {
        stoves.acquire(1);
        Thread.sleep(time * 1000L);
        stoves.release(1);
    }

    @Override
    public void run() {
        try {
            while (Orders.noMoreFoods()) {
                prepareFood(Orders.findFoodByPriorityAndComplexity(this.cookRank));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
