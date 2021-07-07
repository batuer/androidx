package com.gusi.java;

/**
 * @author Ylw
 * @since 2020/5/12 23:10
 */
public class A {
    private B mB;
    private C mC;

    public void setB(B b) {
        mB = b;
        System.out.println("setB() = " + mB);
    }

    public void test() {
        if (mC == null) {
            mC = new C(mB);
        }
        mC.run();
    }

    private class C {
        private B mB;

        public C(B b) {
            mB = b;
        }

        public void run() {
            System.out.println(":--: " + mB);
        }
    }
}
