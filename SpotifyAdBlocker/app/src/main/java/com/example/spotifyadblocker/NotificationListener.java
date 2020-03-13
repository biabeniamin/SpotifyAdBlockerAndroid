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

    private void setVolume(int set_volume)
    {
        AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        oldVolume =mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,set_volume, 0);
    }

    private void mute()
    {
        setVolume(0);
        muted = true;
    }

    private void unmute()
    {
        setVolume(oldVolume);
        muted = false;
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG,"**********  onNotificationPosted");
        Log.i(TAG,"**********  onNotificationPosted "+sbn.getPackageName());
        if(sbn ==null)
            return;
        if(sbn.getNotification() ==null)
            return;
        Log.i(TAG,"**********  passed");
        if(!sbn.getPackageName().equalsIgnoreCase("com.spotify.music"))
            return;
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        if(sbn.getNotification().tickerText.toString().contains("Advertisement"))
        {
            if(muted)
                return;
            mute();
            return;
        }

        if(muted)
            unmute();

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG,"********** onNOtificationRemoved");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
    }
}
