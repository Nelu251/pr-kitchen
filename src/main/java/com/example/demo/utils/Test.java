package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Semaphore;

public class Test {
static Semaphore semaphore = new Semaphore(2);
static int a = 0;
    public static synchronized void isNushCe(Thread thread) throws InterruptedException {
        thread.notify();
    }

    public static void main(String[] args) {
//        Thread thread1 = new Thread(()-> {
//            try {
//                useMe();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        Thread thread2 = new Thread(()-> {
//            try {
//                useMe();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        Thread thread3 = new Thread(()-> {
//            try {
//                useMe();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//
//        thread1.join();
//        thread2.join();
//        thread3.join();
//
//        List<ObjectHz> list = new ArrayList<>();
//
//        list.add(new ObjectHz(1,10));
//        list.add(new ObjectHz(2,10));
//        list.add(new ObjectHz(3,11));
//        list.add(new ObjectHz(3,10));
//        list.add(new ObjectHz(2,9));
//        list.add(new ObjectHz(2,8));
//        list.add(new ObjectHz(1,11));
//        list.add(new ObjectHz(2,12));
//        list.add(new ObjectHz(1,10));
//        list.add(new ObjectHz(3,14));
//        list.add(new ObjectHz(2,9));
//        list.add(new ObjectHz(3,6));
//
//        list.sort(new TestComp());
//
//        list.forEach(System.out::println);
//
//        @Override
////        public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
//            String date = "2021-12-22T08:55:07.92+02:00";
//
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            format.setTimeZone(TimeZone.getTimeZone("UTC"));
//            java.util.Date temp;
//            try {
//                temp = format.parse(date);
//                System.out.println(temp);
//            } catch (ParseException exp) {
//                System.err.println(exp.getMessage());
//            }
        System.out.println(System.currentTimeMillis());


    }

    public static void useMe() throws InterruptedException {
        semaphore.acquire(1);
        a+=1;
        System.out.println(a);
        Thread.sleep(10000);
        semaphore.release(1);
    }
}
