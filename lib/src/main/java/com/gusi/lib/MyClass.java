package com.gusi.lib;

public class MyClass {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Client.connect();
//                }
//            }).start();
//            Client.connect();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
