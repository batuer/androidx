package com.gusi.java;

public class YlwClass {

    public static void main(String[] args) {
        A a = new A();
        a.test();
        a.test1();
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);

    }

    static class MyThread extends Thread {
        public MyThread(Runnable runnable) {
            super(runnable);
        }

        @Override
        public void run() {
            test();
            super.run();
            System.out.println("------run----");
        }

        private void test() {
            System.out.println("---test---" + isAlive());
        }
    }
}
