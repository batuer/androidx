package com.gusi.java;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ylw
 * @since 2020/12/19 13:16
 */
public class MyThreadPoolExecutor {
  private ThreadPoolExecutor mExecutor;

  public MyThreadPoolExecutor() {
    MyBlockQueue blockingQueue = new MyBlockQueue();
    ThreadFactory threadFactory = new ThreadFactory() {
      private final AtomicInteger threadNumber = new AtomicInteger(1);

      @Override
      public Thread newThread(@NotNull Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("pool: " + threadNumber.getAndIncrement());
        return thread;
      }
    };
    mExecutor = new ThreadPoolExecutor(1, 2,
        1L, TimeUnit.SECONDS,
        blockingQueue, threadFactory);
    blockingQueue.setExecutor(mExecutor);
  }


  public void execute(MyRunnable runnable) {
    mExecutor.execute(runnable);
  }

  public class MyBlockQueue extends LinkedBlockingQueue<Runnable> {
    private HashSet mWorkers;


    public void setExecutor(ThreadPoolExecutor executor) {
      Class<? extends ThreadPoolExecutor> aClass = executor.getClass();
      Field[] fields = aClass.getDeclaredFields();
      System.out.println(":--:"  + fields.length);
      for (Field field : fields) {
        try {
          field.setAccessible(true);
          System.out.println(field + " :--: " + field.get(executor));
        } catch (IllegalAccessException e) {
          System.err.println(field + " :--: " + e.toString());
        }
      }
    }

    @Override
    public boolean offer(@NotNull Runnable runnable) {
      System.out.println(runnable + " :--: " + mWorkers.size() + " : " + size());
      if (mWorkers.size() < 2) {
        return false;
      }
      boolean offer = super.offer(runnable);
      System.out.println(runnable + " :==: " + mWorkers.size() + " : " + size());
      return offer;
    }
  }

  public static class MyRunnable implements Runnable {
    private String mFlag;

    public MyRunnable(String mFlag) {
      this.mFlag = mFlag;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " : " + mFlag);
    }

    @Override
    public String toString() {
      return "Runnable{" + mFlag + '}';
    }
  }
}
