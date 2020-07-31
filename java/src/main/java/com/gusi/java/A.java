package com.gusi.java;

/**
 * @author Ylw
 * @since 2020/5/12 23:10
 */
public class A {
    private B b;
    private int num = -1;

    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(num + " :====: " + b);
                }
            }
        }).start();
    }
//        -1:====:null
//        -1:====:null
//        ------------------------------
//        -1:====:null
//        -1:====:null
//        -1:====:null
//        -1:====:null
//        -1:====:null
//        -------------:com.gusi.java.B@2a139a55
//-1:====:com.gusi.java.B@2a139a55
//-------------:1
//        1:====:com.gusi.java.B@2a139a55
//1:====:com.gusi.java.B@2a139a55

    public void test1() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------");
        b = new B();
        System.out.println("-------------:" + b);
        num = 1;
        System.out.println("-------------:" + num);
    }
}
