package com.gusi.androidx.module.lock;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.gusi.androidx.R;
import com.gusi.androidx.app.App;
import com.gusi.androidx.base.BaseActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    public void reflect(View view) {
        System.out.println("-----Demo6-----\n\n");

        try {
            Class<?> class1 = Class.forName(App.class.getName());
            String nameString = class1.getClassLoader().getClass().getName();
            System.out.println("Demo6: 类加载器类名: " + nameString);

            System.out.println("-----获取一个系统的类加载器(系统的类加载器，可以获取，当前这个类就是它加载的)-----");
            // 1. 获取一个系统的类加载器(系统的类加载器 可以获取，当前这个类就是它加载的)
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            System.out.println(classLoader);

            System.out.println("-----获取系统类加载器的父类加载器（扩展类加载器，可以获取）-----");
            // 2. 获取系统类加载器的父类加载器（扩展类加载器，可以获取）.
            classLoader = classLoader.getParent();
            System.out.println(classLoader);

            System.out.println("-----获取扩展类加载器的父类加载器（引导类加载器，不可获取）-----");
            // 3. 获取扩展类加载器的父类加载器（引导类加载器，不可获取）.
            classLoader = classLoader.getParent();
            System.out.println(classLoader);

            System.out.println("-----测试当前类由哪个类加载器进行加载（系统类加载器）-----");
            // 4. 测试当前类由哪个类加载器进行加载（系统类加载器）:
            classLoader = Class.forName(getClass().getName()).getClassLoader();
            System.out.println(classLoader);
            ClassLoader classLoader1 = getClassLoader();
            System.out.println("==:" + classLoader1);

            System.out.println("-----测试 JDK 提供的 Object 类由哪个类加载器负责加载（引导类加载器，不可获取）-----");
            // 5. 测试 JDK 提供的 Object 类由哪个类加载器负责加载（引导类加载器，不可获取）
            classLoader = Class.forName("java.lang.Object").getClassLoader();
            System.out.println(classLoader);
        } catch (ClassNotFoundException e) {
            Log.e("Fire", "LockActivity:172行:" + e.toString());
        }
    }

    /**
     * 应用启动状态跟踪 一般写在application里面的attachBaseContext()方法里面，因为这个方法时机最早
     *
     * @param context context
     * @throws Exception
     */
    public static void hookHandler(Context context) throws Exception {
        // 反射获取ActivityThread的Class对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        // 获取currentActivityThread私有方法
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        // 执行currentActivityThreadMethod获取主线程对象
        Object activityThread = currentActivityThreadMethod.invoke(null);
        // 获取mH字段
        Field mH = activityThreadClass.getDeclaredField("mH");
        mH.setAccessible(true);
        // 获取mH私有字段的值
        Handler handler = (Handler) mH.get(activityThread);
        // 反射获取Handler中原始的mCallBack字段
        Field mCallBack = Handler.class.getDeclaredField("mCallback");
        mCallBack.setAccessible(true);
        // 这里设置了我们自己实现了接口的CallBack对象
        mCallBack.set(handler, new CustomHandler(handler));
    }

    public void Broadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("com.gusi.ylwylw");
        sendOrderedBroadcast(intent, "com.gusi.ylw");
        Log.w("Fire", "LockActivity:Broadcast:end");
    }

    public void Broadcast1(View view) {

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse("content://com.android.contacts/data"), new String[]{"data1", "data4"
                , "_id"}, null, null, null);
        Log.e(TAG, "Broadcast1: " + cursor.getCount());
        cursor.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        });

    }

    public void Ams(View view) {
        try {
            Class<?> aClass = Class.forName("android.os.ServiceManager");
            Method method = aClass.getMethod("getService", String.class);
            Object invoke = method.invoke(null, "activity");
            Log.w(TAG, "Ams: " + invoke);
            IBinder iBinder = (IBinder) invoke;

            for (int i = 0; i < 10; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = Thread.currentThread().getName();
                        while (true) {
                            try {
                                Parcel data = Parcel.obtain();
                                data.writeString("dataYlw: " + name);
                                Parcel reply = Parcel.obtain();
                                reply.writeString("replyYlw: " + name);
                                boolean transact = iBinder.transact(100, data, reply, IBinder.FLAG_ONEWAY);
                                Log.d("Fire", "LockActivity:263行: " + name + ":--:" + transact);
                            } catch (RemoteException e) {
                                Log.e("Fire", "LockActivity:264行:" + name + " : " + e.toString());
                            }
                            SystemClock.sleep(1);
                        }
                    }
                }).start();
            }

        } catch (Exception e) {
            Log.e("Fire", "LockActivity:251行:" + e.toString());
        }
    }

    /**
     * 用于应用初始化异步通信Handler,可以截获发送的一系列事件
     */
    public static class CustomHandler implements Handler.Callback {
        private Handler origin;

        public CustomHandler(Handler mHandler) {
            this.origin = mHandler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            // 这样每次启动的时候可以做些额外的事情
            origin.handleMessage(msg);
            return false;
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
