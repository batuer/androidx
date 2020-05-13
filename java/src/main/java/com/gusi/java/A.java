package com.gusi.java;

/**
 * @author Ylw
 * @since 2020/5/12 23:10
 */
public class A {
    private B mB;

    private A() {
        // no instance
    }

    public static A getInstance() {
        return Holder.single;
    }

    private static final class Holder {
        private static final A single = new A();
    }

    public void setB(B b) {
        mB = b;
    }

    public void test() {
        B b = mB;
        if (mB != null) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                mB.test();
            } catch (Exception e) {
                System.err.println(mB +" :---------------------: " + b);
            }

        }
    }

    public void test1() {
        B b = mB;
        if (b != null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b.test();

        }
    }

}
