package com.custom;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.err;

public class ThreadsTest extends AbstractTestNGSpringContextTests {

    @Test
    public void hashMapTest() {
        HashMap<String, String> hashMap = new HashMap<>();
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(20);

        for( int i=0; i<1000; i++) {
            final String val = Integer.toString(i);
            threadPoolExecutor.submit(()->hashMap.put(val, val));
        }
        err.println("size=" + hashMap.size());
    }

    @Test
    public void synchronizedHashMapTest() {
        Map<String, String> syncHashMap = Collections.synchronizedMap(new HashMap<>());
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(20);

        long time = currentTimeMillis();
        for( int i=0; i<1000; i++) {
            final String val = Integer.toString(i);
            threadPoolExecutor.submit(()-> {
                syncHashMap.put(val, val);
            });
        }
        err.println("size=" + syncHashMap.size() + " Time= " + (currentTimeMillis()-time));
    }

    @Test
    public void concurrentHashMapTest() {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(20);

        long time = currentTimeMillis();
        for( int i=0; i<1000; i++) {
            final String val = Integer.toString(i);
            threadPoolExecutor.submit(()->concurrentHashMap.put(val, val));
        }
        err.println("size=" + concurrentHashMap.size() + " Time= " + (currentTimeMillis()-time));
    }

    @Test
    public void RunnableCallableTest () {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable r = () -> err.println("inside runnable");
        Callable c = () -> {
            err.println("inside callable");
            return "returned from callable";
        };

        try {
            err.println("result from callable " + executorService.submit(c).get() + " time=" + currentTimeMillis());
            err.println("result from runnable " + executorService.submit(r).get() + " time=" + currentTimeMillis());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> err.println("barrier action"));
        Arrays.asList(
                createNewThreadWithMessage(cyclicBarrier, "inside runnable 1 " + currentTimeMillis()),
                createNewThreadWithMessage(cyclicBarrier, "inside runnable 2 " + currentTimeMillis()),
                createNewThreadWithMessage(cyclicBarrier, "inside runnable 3 " + currentTimeMillis()),
                createNewThreadWithMessage(cyclicBarrier, "inside runnable 4 " + currentTimeMillis()))
                .forEach(Thread::start);
    }

    private Thread createNewThreadWithMessage(CyclicBarrier cb, String msg) {
        return new Thread(() -> {
            try {
                cb.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            err.println(msg);
        });
    }

    @Test
    public void threadTest() {
        Runnable r = () -> err.println("inside runnable");
        Thread th = new Thread(r) {
            @Override
            public void run() {
                super.run();
                err.println("inside thread");
            }
        };
        th.start();
    }
}
