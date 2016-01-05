package com.example.saxonzhang.learnservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private boolean running = false;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service create");

        startRunningThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("on start command");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        System.out.println("service destroy");
    }

    private void startRunningThread() {
        running = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    System.out.println("服务正在运行...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
