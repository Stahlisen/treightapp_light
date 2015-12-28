package com.example.myfitnessjourney.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by fredrikstahl on 15-12-13.
 */
public class AlarmTriggerReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent executeAlarmService = new Intent(context, AlarmTriggerService.class);
        context.startService(executeAlarmService);
    }
}
