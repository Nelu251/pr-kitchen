package com.example.demo.service;

import com.example.demo.domain.dto.PreparedOrder;
import com.example.demo.domain.model.Cook;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CookService {

    private final RestTemplate restTemplate;

    boolean flag = false;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CookService cookService;

    public CookService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String sendPreparedOrder(PreparedOrder preparedOrder) {
        HttpEntity<PreparedOrder> entity = new HttpEntity<>(preparedOrder);
        return restTemplate.exchange("http://localhost:8080/distribution", HttpMethod.POST, entity, String.class).getBody();
    }

    public void cooking() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Cook cook1 = applicationContext.getBean(Cook.class);
        cook1.setId(1);
        cook1.setRank(1);
        cook1.setProficiency(1);
        Thread thread1 = new Thread(cook1);
        executorService.submit(cook1);

        Cook cook2 = applicationContext.getBean(Cook.class);
        cook2.setId(2);
        cook2.setRank(2);
        cook2.setProficiency(2);
        Thread thread2 = new Thread(cook2);
        executorService.submit(cook2);

        Cook cook3 = applicationContext.getBean(Cook.class);
        cook3.setId(3);
        cook3.setRank(3);
        cook3.setProficiency(3);
        Thread thread3 = new Thread(cook3);
        executorService.submit(cook3);

        Cook cook4 = applicationContext.getBean(Cook.class);
        cook4.setId(4);
        cook4.setRank(3);
        cook4.setProficiency(4);
        Thread thread4 = new Thread(cook4);
        executorService.submit(cook4);

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    void letsGo() {
        flag = true;
        cookService.cooking();
    }

}
