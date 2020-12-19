package com.gusi.java;

public class YlwClass {

  public static void main(String[] args) {
    MyThreadPoolExecutor executor = new MyThreadPoolExecutor();
    executor.execute(new MyThreadPoolExecutor.MyRunnable("11"));
  }

  private static void sleep(MyThreadPoolExecutor executor, int i2) {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    executor.execute(new MyThreadPoolExecutor.MyRunnable("" + i2));
  }
}
