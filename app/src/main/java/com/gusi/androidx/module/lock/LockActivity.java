package com.gusi.androidx.module.lock;

import android.util.Log;
import android.view.View;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseActivity;

public class LockActivity extends BaseActivity {
    private static final String TAG = "LockActivity";
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    @Override
    protected int getLayout() {
        return R.layout.activity_lock;
    }

    @Override
    protected void initInject() {

    }

    public void lock(View view) {
        lock1();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Log.e("Fire", "LockActivity:29行:" + e.toString());
        }
        Log.w("Fire", "LockActivity:31行:");
        synchronized (lock2) {
            Log.w("Fire", "LockActivity:33行:");
            synchronized (lock1) {
                Log.w("Fire", "LockActivity:35行:");
            }
            Log.w("Fire", "LockActivity:37行:");
        }
        Log.w("Fire", "LockActivity:39行:");
    }

    private void lock1() {
        new Thread(() -> {
            Log.w("Fire", "LockActivity:40行:lock1  start:" + lock1);
            synchronized (lock1) {
                Log.w("Fire", "LockActivity:42行:lock2 start:" + lock2);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e("Fire", "LockActivity:45行:" + e.toString());
                }
                synchronized (lock2) {
                }
                Log.w("Fire", "LockActivity:52行:lock2 end:" + lock2);
            }
            Log.w("Fire", "LockActivity:54行:lock1 end:" + lock1);
        }).start();
    }

    public void order(View view) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                orderLock1(i);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                orderLock2(i);
            }
        }).start();
    }

    private void orderLock1(int i) {
        synchronized (lock1) {
            Log.d(TAG, "orderLock1: orderLock1:" + i);
            synchronized (lock2) {
                Log.d(TAG, "orderLock1: orderLock2:" + i);
            }
        }
    }

    private void orderLock2(int i) {
        synchronized (lock2) {
            Log.d(TAG, "orderLock2: orderLock2:" + i);
            synchronized (lock1) {
                Log.d(TAG, "orderLock2: orderLock1:" + i);
            }
        }
    }

    public void transfer(View view) {
        Account A = new Account("A", 100);
        Account B = new Account("B", 200);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    transfer(A, B, 1);
                } catch (Exception e) {
                    Log.e("Fire", "LockActivity:63行:" + e.toString());
                }
            }
        });
        t1.setName("T1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    transfer(B, A, 1);
                } catch (Exception e) {
                    Log.e("Fire", "LockActivity:74行:" + e.toString());
                }
            }
        });
        t2.setName("T2");

        t1.start();
        t2.start();
    }

    public void transfer(Account from, Account to, int amount) throws Exception {
        synchronized (from) {
            Log.d(TAG, "transfer: " + "线程【{}】获取【{}】账户锁成功..." + Thread.currentThread().getName() + from.name);
            synchronized (to) {
                Log.d(TAG, "transfer: " + "线程【{}】获取【{}】账户锁成功..." + Thread.currentThread().getName() + to.name);
                if (from.balance < amount) {
                    throw new Exception("余额不足");
                } else {
                    from.debit(amount);
                    to.credit(amount);
                    Log.d(TAG, "transfer: " + "线程【{}】从【{}】账户转账到【{}】账户【{}】元钱成功" + Thread.currentThread().getName()
                        + from.name + to.name + amount);
                }
            }
        }
    }

    private static class Account {
        String name;
        int balance;

        public Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        void debit(int amount) {
            this.balance = balance - amount;
        }

        void credit(int amount) {
            this.balance = balance + amount;
        }
    }

}
