package com.gusi.java;

public class YlwClass {

    public static void main(String[] args) {
        System.out.println("----------------");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (;;) {
                        A.getInstance().setB(null);
                    }
                }
            });
            thread.setName("11111:-----: " + i);
            thread.start();
        }
        for (int i = 0; i < 2; i++) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (;;) {
                        A.getInstance().setB(new B());
                    }

                }
            });
            thread1.setName("2222222:---------:  " + i);
            thread1.start();
        }

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    A.getInstance().test();
                }
            }
        });
        thread2.setName("3333333333333");
        thread2.start();
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    A.getInstance().test1();
                }

            }
        });
        thread3.setName("4444444444");
        thread3.start();
    }
}
