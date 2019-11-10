package com.gusi.lib;

/**
 * @author Ylw
 * @since 2019/10/30 22:11
 */
public class MyThread {
    // private final Object mLock1 = new Object();
    private final Object mLock = new Object();

    public void sleepTest() {
        sleep1();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sleep2();
    }

    private void sleep1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("sleep1 start");
                synchronized (mLock) {
                    System.out.println("sleep1 synchronized start");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("sleep1:" + e.toString());
                    }
                    System.out.println("sleep1 synchronized end");
                }
                System.out.println("sleep1 end");
            }
        }).start();
    }

    private void sleep2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("sleep2 start");
                synchronized (mLock) {
                    System.out.println("sleep2 synchronized start");
                    System.out.println("sleep2 synchronized end");
                }
                System.out.println("sleep2 end");
            }
        }).start();
    }

    public void waitTest() {
        wait1();
        wait3();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait2();
    }

    private void wait1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("wait1 start");
                synchronized (mLock) {
                    System.out.println("wait1 synchronized start");
                    try {
                        mLock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("wait1 :" + e.toString());
                    }
                    System.out.println("wait1 synchronized end");
                }
                System.out.println("wait1 end");
            }
        }).start();
    }

    private void wait3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("wait3 start");
                synchronized (mLock) {
                    System.out.println("wait3 synchronized start");
                    try {
                        mLock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("wait3 :" + e.toString());
                    }
                    System.out.println("wait3 synchronized end");
                }
                System.out.println("wait3 end");
            }
        }).start();
    }

    private void wait2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("wait2 start");
                synchronized (mLock) {
                    System.out.println("wait2 synchronized start");
//                    mLock.notify();
                     mLock.notifyAll();
                    System.out.println("wait2 synchronized end");
                }
                System.out.println("wait2 end");
            }
        }).start();
    }

}
