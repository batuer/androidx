package com.gusi.java;

public class YlwClass {

    public static void main(String[] args) {
        MyThread myThread = new MyThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("==========run===========");
            }
        });
        System.out.println("=========" + myThread.isAlive());
        myThread.start();
        System.out.println(myThread.getPriority() + "-----" + myThread.isAlive());
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
