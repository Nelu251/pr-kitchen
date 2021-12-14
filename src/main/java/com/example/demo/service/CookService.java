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
        Thread[] threads = new Thread[3];
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            Cook cook = applicationContext.getBean(Cook.class);
            cook.setId(i);
            threads[i] = new Thread(cook);
            executorService.submit(cook);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    void letsGo() {
        cookService.cooking();
    }

}
