package com.nvapps.resolve;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ResolutionReceiver extends BroadcastReceiver {

    private static final String TAG = "ResolutionReceiver";
    private static final int NOTIFICATION_ID = 1;

    public ResolutionReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: Received Intent");

        // Send Notification
        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("category"))
                .setSmallIcon(R.drawable.ic_add);

        manager.notify(NOTIFICATION_ID, builder.build());

    }
}
