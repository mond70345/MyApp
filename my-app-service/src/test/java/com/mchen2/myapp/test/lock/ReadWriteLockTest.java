package com.mchen2.myapp.test.lock;

import cn.hutool.core.thread.ThreadUtil;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    private static final Map<String, String> redis = Maps.newHashMap();

    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(Boolean.FALSE);

    public static void main(String[] args) {
        new Thread(() -> {
            final Lock lock = readWriteLock.writeLock();
            try {
                if (lock.tryLock(10, TimeUnit.SECONDS)) {
                    System.out.println(Thread.currentThread().getName() + " " + "writeLock is lock");
                    System.out.println(Thread.currentThread().getName() + " " + "do somethings");
                    ThreadUtil.safeSleep(20000L);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("系统繁忙，获取数据失败");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " " + "writeLock is unlock");
            }
        }).start();
        new Thread(() -> {
            final Lock lock = readWriteLock.readLock();
            try {
                if (lock.tryLock(10, TimeUnit.SECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " " + "readLock is lock");
                        System.out.println(Thread.currentThread().getName() + " " + "do somethings");
                        ThreadUtil.safeSleep(3000L);
                    } finally {
                        lock.unlock();
                        System.out.println(Thread.currentThread().getName() + " " + "readLock is unlock");
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " " + "系统繁忙，获取数据失败");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            final Lock lock = readWriteLock.readLock();
            try {
                if (lock.tryLock(10, TimeUnit.SECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " " + "readLock is lock");
                        System.out.println(Thread.currentThread().getName() + " " + "do somethings");
                        ThreadUtil.safeSleep(1000L);
                    }  finally {
                        lock.unlock();
                        System.out.println(Thread.currentThread().getName() + " " + "readLock is unlock");
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " " + "系统繁忙，获取数据失败");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String  getData(String id) {
        if (redis.containsKey(id)) {
            return redis.get(id);
        }

        Lock lock = readWriteLock.writeLock();
//        try {
//            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
//                try {
//
//                }
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return "";
    }
}
