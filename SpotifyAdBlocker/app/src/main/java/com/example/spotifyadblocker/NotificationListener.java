package com.example.spotifyadblocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationListener  extends NotificationListenerService {
    private String TAG = this.getClass().getSimpleName();
    private int oldVolume = 0;
    private boolean muted = false;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void mute()
    {
        AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        oldVolume =mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int set_volume=0;
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,set_volume, 0);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG,"**********  onNotificationPosted");
        if(!sbn.getPackageName().equalsIgnoreCase("com.spotify.music"))
            return;
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        if(!sbn.getNotification().tickerText.toString().contains("Adver"))
        {
            mute();
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG,"********** onNOtificationRemoved");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
    }
}
