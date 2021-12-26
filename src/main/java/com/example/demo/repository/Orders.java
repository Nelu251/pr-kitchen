package com.example.demo.repository;

import com.example.demo.domain.dto.OrderRequest;
import com.example.demo.domain.model.enums.CookingApparatus;
import com.example.demo.utils.OrderComparator;
import java.util.Collections;
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

    //    public static synchronized List<Integer> findFoodByComplexityAndAvailableResource(CookingApparatus cookingApparatus, int rank) {
    //        List<Integer> list = new ArrayList<>();
    //        safeList.sort(new OrderComparator());
    //
    //        for (OrderRequest orderRequest : safeList) {
    //            for (int i = 0; i < orderRequest.getItems().size(); i++) {
    //                int foodId = orderRequest.getItems().get(i);
    //                if (foodId == -1) {
    //                } else if ((Foods.getFoodById(foodId).getComplexity() == rank || Foods.getFoodById(foodId).getComplexity() == rank - 1)
    //                    && Foods.getFoodById(foodId).getCookingApparatus().equals(cookingApparatus)) {
    //                    log.info("found food with id : {}", foodId);
    //
    //                    list.add(foodId);
    //                    list.add(orderRequest.getOrder_id());
    //                    orderRequest.getItems().set(i, -1);
    //                    return list;
    //                }
    //            }
    //        }
    //        return Collections.emptyList();
    //    }
    //
    //    public static synchronized List<Integer> tryFindFoodByComplexityAndAvailableResource(CookingApparatus cookingApparatus, int rank) {
    //        List<Integer> list = new ArrayList<>();
    //        safeList.sort(new OrderComparator());
    //
    //        for (OrderRequest orderRequest : safeList) {
    //            for (int i = 0; i < orderRequest.getItems().size(); i++) {
    //                int foodId = orderRequest.getItems().get(i);
    //                if (foodId == -1) {
    //                } else if ((Foods.getFoodById(foodId).getComplexity() == rank || Foods.getFoodById(foodId).getComplexity() == rank - 1)
    //                    && Foods.getFoodById(foodId).getCookingApparatus().equals(cookingApparatus)) {
    //                    log.info("found food with id : {}", foodId);
    //
    //                    list.add(foodId);
    //                    list.add(orderRequest.getOrder_id());
    //                    list.add(i);
    //
    //                    return list;
    //                }
    //            }
    //        }
    //        return Collections.emptyList();
    //    }
    //
    //    public static synchronized List<Integer> findFoodWithNullAparatus(int rank) {
    //        List<Integer> list = new ArrayList<>();
    //        safeList.sort(new OrderComparator());
    //
    //        for (OrderRequest orderRequest : safeList) {
    //            for (int i = 0; i < orderRequest.getItems().size(); i++) {
    //                int foodId = orderRequest.getItems().get(i);
    //                if (foodId == -1) {
    //                } else if ((Foods.getFoodById(foodId).getComplexity() == rank || Foods.getFoodById(foodId).getComplexity() == rank - 1) && Foods.getFoodById(foodId).getCookingApparatus() == null) {
    //                    log.info("found food with id : {}", foodId);
    //
    //                    list.add(foodId);
    //                    list.add(orderRequest.getOrder_id());
    //                    orderRequest.getItems().set(i, -1);
    //                    return list;
    //                }
    //            }
    //        }
    //        return Collections.emptyList();
    //    }
    //
    //    public static synchronized List<Integer> tryFindFoodWithNullAparatus(int rank) {
    //        List<Integer> list = new ArrayList<>();
    //        safeList.sort(new OrderComparator());
    //
    //        for (OrderRequest orderRequest : safeList) {
    //            for (int i = 0; i < orderRequest.getItems().size(); i++) {
    //                int foodId = orderRequest.getItems().get(i);
    //                if (foodId == -1) {
    //                } else if ((Foods.getFoodById(foodId).getComplexity() == rank || Foods.getFoodById(foodId).getComplexity() == rank - 1) && Foods.getFoodById(foodId).getCookingApparatus() == null) {
    //                    log.info("found food with id : {}", foodId);
    //
    //                    list.add(foodId);
    //                    list.add(orderRequest.getOrder_id());
    //                    list.add(i);
    //                    return list;
    //                }
    //            }
    //        }
    //        return Collections.emptyList();
    //    }

    public static synchronized List<Integer> findFoodByPriorityAndComplexity(int rank) {
        List<Integer> list = new ArrayList<>();
        safeList.sort(new OrderComparator());

        for (OrderRequest orderRequest : safeList) {
            for (int i = 0; i < orderRequest.getItems().size(); i++) {
                int foodId = orderRequest.getItems().get(i);
                if (foodId != -1 && Foods.getFoodById(foodId).getComplexity() == rank) {
                    log.info("found food with id : {}", foodId);

                    list.add(foodId);
                    list.add(orderRequest.getOrder_id());
                    orderRequest.getItems().set(i, -1);
                    return list;
                }
            }
        }
        for (OrderRequest orderRequest : safeList) {
            for (int i = 0; i < orderRequest.getItems().size(); i++) {
                int foodId = orderRequest.getItems().get(i);
                if (foodId == -1) {
                } else if (Foods.getFoodById(foodId).getComplexity() == rank - 1) {
                    log.info("found food with id : {}", foodId);

                    list.add(foodId);
                    list.add(orderRequest.getOrder_id());
                    orderRequest.getItems().set(i, -1);
                    return list;
                }
            }
        }

        log.warn("this thread can't find any foods left : {}", Thread.currentThread());
        return null;
    }

    //    public static synchronized List<Integer> findFood() throws NoSuchElementException {
    //        List<Integer> list = new ArrayList<>();
    //
    //        for (OrderRequest orderRequest : safeList) {
    //            for (int i = 0; i < orderRequest.getItems().size(); i++) {
    //                if (orderRequest.getItems().get(i) == -1) {
    //                } else {
    //                    log.info("found food with id : {}", orderRequest.getItems().get(i));
    //                    list.add(orderRequest.getItems().get(i));
    //                    list.add(orderRequest.getOrder_id());
    //                    orderRequest.getItems().set(i, -1);
    //                    return list;
    //                }
    //            }
    //        }
    //
    //        log.warn("this thread can't find any foods left : {}", Thread.currentThread());
    //        return null;
    //    }

    //    public static synchronized boolean noMoreFoods() {
    //        boolean flag = false;
    //
    //        for (OrderRequest orderRequest : safeList) {
    //            for (Integer item : orderRequest.getItems()) {
    //                if (item == -1) {
    //                    flag = true;
    //                } else {
    //                    flag = false;
    //                }
    //            }
    //        }
    //        return flag;
    //    }

    public static boolean isOrderInPending(int orderId) {
        boolean flag = false;

        for (Integer foodNr : safeList.stream().filter(orderRequest -> orderRequest.getOrder_id() == orderId).findFirst().get().getItems()) {
            if (foodNr == -1) {
                flag = true;
            }
        }

        return flag;
    }

    public static synchronized boolean noMoreFoods() {
        for (OrderRequest orderRequest : safeList) {
            for (Integer item : orderRequest.getItems()) {
                if (item != -1) {
                    return true;
                }
            }
        }
        return false;
    }
}