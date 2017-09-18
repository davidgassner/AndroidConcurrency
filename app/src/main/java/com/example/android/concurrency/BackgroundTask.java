package com.example.android.concurrency;

import android.util.Log;

public class BackgroundTask implements Runnable {

    public static final String TAG = "CodeRunner";
    private int threadNumber;

    public BackgroundTask(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {

        Log.i(TAG, Thread.currentThread().getName() +
                " start, thread number = " + threadNumber);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, Thread.currentThread().getName() +
                " end, thread number = " + threadNumber);

    }
}
