package com.example.saxonzhang.learnservice;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private ArrayList<Integer> bottons;
    private Intent myServiceIntent;
    private Handler handler;
    private boolean status = false;
    private Thread updateThread;
    private boolean activityRunning = false;
    private Runnable updateStatus = new Runnable() {
        @Override
        public void run() {
            if (status) {
                ((Button)findViewById(R.id.btnStatus)).setBackgroundColor(Color.GREEN);
                ((Button)findViewById(R.id.btnStatus)).setText("服务正在运行...");
            } else {
                ((Button)findViewById(R.id.btnStatus)).setBackgroundColor(Color.RED);
                ((Button)findViewById(R.id.btnStatus)).setText("服务已停止");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (myServiceIntent == null) {
            myServiceIntent = new Intent(this, MyService.class);
        }

        if (bottons == null) {
            bottons = new ArrayList<Integer>();
            bottons.add(R.id.btnStartService);
            bottons.add(R.id.btnStopService);
            bottons.add(R.id.btnBindService);
            bottons.add(R.id.btnUnbindService);
            for (Integer id : bottons) {
                findViewById(id).setOnClickListener(this);
            }
        }

        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }

        if (updateThread == null) {
            updateThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (activityRunning) {
                        //System.out.println(Thread.currentThread().getName() + " running");
                        boolean newstatus = isServiceRunning(MainActivity.this);
                        if (newstatus != status) {
                            status = newstatus;
                            handler.postAtFrontOfQueue(updateStatus);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            updateThread.setName("updateThread_" + System.currentTimeMillis());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        updateThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityRunning = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                startService(myServiceIntent);
                break;
            case R.id.btnStopService:
                stopService(myServiceIntent);
                break;
            case R.id.btnBindService:
                bindService(myServiceIntent, this, BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindService:
                try {
                    unbindService(this);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("Service connected");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        System.out.println("Service disconnected");
    }

    public static boolean isServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.saxonzhang.learnservice.MyService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
