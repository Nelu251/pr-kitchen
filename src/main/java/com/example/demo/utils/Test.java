package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Test {
static Semaphore semaphore = new Semaphore(2);
static int a = 0;
    public static synchronized void isNushCe(Thread thread) throws InterruptedException {
        thread.notify();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(()-> {
            try {
                useMe();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(()-> {
            try {
                useMe();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(()-> {
            try {
                useMe();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

    }

    public static void useMe() throws InterruptedException {
        semaphore.acquire(1);
        a+=1;
        System.out.println(a);
        Thread.sleep(10000);
        semaphore.release(1);
    }
}
