package com.mchen2.myapp.test.crypto;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadTest {

    private static AtomicBoolean resA = new AtomicBoolean(Boolean.TRUE);

    private static AtomicBoolean resB = new AtomicBoolean(Boolean.TRUE);

    private static AtomicBoolean resC = new AtomicBoolean(Boolean.TRUE);

    public static void main(String[] args) {
        // 通过CAS自旋，如果获取二级锁失败，则主动释一级放锁
//        Printer a = new Printer(resA, resB);
//        Printer b = new Printer(resB, resC);
//        Printer c = new Printer(resC, resA);
//        new Thread(a).start();
//        new Thread(b).start();
//        new Thread(c).start();

        // 通过wait方法释放其中一个锁，唤醒其它线程来抢占锁
        Printer2 a = new Printer2(resA, resB);
        Printer2 b = new Printer2(resB, resC);
        Printer2 c = new Printer2(resC, resA);
        new Thread(a).start();
        new Thread(b).start();
        new Thread(c).start();
    }

    public static class Printer2 implements Runnable {
        public Printer2(Object lockA, Object lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        private final Object lockA;
        private final Object lockB;

        @Override
        public void run() {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "已获取一级锁，正在执行");
                if (lockA == resA) {
                    try {
                        lockA.wait(50L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "已获二级取锁，正在执行");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "结束");
                    lockB.notify();
                }
            }
        }
    }


    public static class Printer implements Runnable {

        public Printer(AtomicBoolean lockA, AtomicBoolean lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        private final AtomicBoolean lockA;
        private final AtomicBoolean lockB;

        @Override
        public void run() {
            while (true) {
                if (lockA.compareAndSet(Boolean.TRUE, Boolean.FALSE)) {
                    synchronized (lockA) {
                        System.out.println(Thread.currentThread().getName() + "已获取一级锁，正在执行");
                        if (lockB.compareAndSet(Boolean.TRUE, Boolean.FALSE)) {
                            synchronized (lockB) {
                                System.out.println(Thread.currentThread().getName() + "已获二级取锁，正在执行");
                                lockA.set(Boolean.TRUE);
                                lockB.set(Boolean.TRUE);
                            }
                            break;
                        } else {
                            System.out.println(Thread.currentThread().getName() + "获取二级锁失败，释放锁继续等待");
                            lockA.set(Boolean.TRUE);
                        }
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + "获取一级锁失败");
                }

                ThreadUtil.safeSleep(500L);
            }
            System.out.println(Thread.currentThread().getName() + "结束");
        }
    }

}
