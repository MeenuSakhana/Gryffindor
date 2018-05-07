package com.example.layout.exp2d;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

/**
 * Created by MEENU SAKHANA S on 22-02-2018.
 */

public class progress extends Activity{
    RingProgressBar rpb1 ,rpb2;
    NotificationCompat.Builder notify1;
    PendingIntent pi1;
    NotificationManager mgr;
    Intent ri;
    TaskStackBuilder tsb;


    int progress = 0;
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0)
            {
                if(progress<100)
                {
                    progress++;
                    rpb1.setProgress(progress);
                    rpb2.setProgress(progress);
                }
            }
            //super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);

        rpb1 = (RingProgressBar)findViewById(R.id.progress_bar_1);
        rpb2 = (RingProgressBar)findViewById(R.id.progress_bar_2);

        rpb1.setOnProgressListener(new RingProgressBar.OnProgressListener() {
            @Override
            public void progressToComplete() {
                Toast.makeText(getApplicationContext(),"Progress Complete",Toast.LENGTH_SHORT).show();
                startNotification();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<100; i++) {
                    try {
                        Thread.sleep(100);
                        myHandler.sendEmptyMessage(0);
                    } catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void startNotification(){
        notify1 = new NotificationCompat.Builder(this);
        notify1.setContentTitle("OREDER PLACED SUCCESSFULLY");
        notify1.setContentText("Order will be delivered within 2 days");
        notify1.setTicker("Order Status");
        notify1.setSmallIcon(R.drawable.water);
        tsb = TaskStackBuilder.create(progress.this);
        tsb.addParentStack(Result.class);
        ri = new Intent(progress.this,Result.class);
        tsb.addNextIntent(ri);
        pi1 = tsb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notify1.setContentIntent(pi1);
        mgr = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mgr.notify(0,notify1.build());
    }
}


