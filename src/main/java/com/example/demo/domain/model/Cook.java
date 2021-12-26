package com.example.demo.domain.model;

import com.example.demo.service.CookService;
import com.example.demo.service.OrderService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
@Slf4j
public class Cook implements Runnable {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    OrderService orderService;

    @Autowired
    CookService cookService;

    private int id;
    private String name;
    private int rank;
    private int proficiency;
    private String catchPhrase;

    @Override
    public void run() {
        try {
            Thread[] threads = new Thread[this.proficiency];
            ExecutorService executorService = Executors.newFixedThreadPool(this.proficiency);
            for (int i = 0; i < this.proficiency; i++) {
                CookFood cookFood = applicationContext.getBean(CookFood.class);
                cookFood.setId(i);
                cookFood.setCookId(this.id);
                cookFood.setCookRank(this.rank);
                threads[i] = new Thread(cookFood);
                executorService.submit(cookFood);
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
