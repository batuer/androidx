package com.gusi.lib;

/**
 * @author Ylw
 * @since 2019/10/30 22:11
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("==========" + Thread.currentThread().getName());
    }

    public void exec(Runnable task) {

        task.run();
    }
}
