package com.element.timespent;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class TimeSpentService extends IntentService {

    public TimeSpentService() {
        super(TimeSpentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime = System.currentTimeMillis();
        String timeSpent = getSpentTime(startTime, endTime);
        Bundle bundle = new Bundle();
        bundle.putString("time", timeSpent);
        resultReceiver.send(0, bundle);
    }

    public String getSpentTime(long timeStart, long timeEnd) {
        String spentTime;
        long totalSeconds = (timeEnd / 1000 - timeStart / 1000);

        String timeFormatSeconds;
        if ((totalSeconds % 60) < 10) timeFormatSeconds = "0" + (totalSeconds % 60);
        else timeFormatSeconds = "" + totalSeconds % 60;

        String timeFormatMinutes;
        if ((totalSeconds / 60) < 10) timeFormatMinutes = "0" + (totalSeconds / 60);
        else timeFormatMinutes = "" + totalSeconds / 60;

        String timeFormatHours;
        if (((totalSeconds / 60) / 60) < 10) timeFormatHours = "0" + ((totalSeconds / 60) / 60);
        else timeFormatHours = "" + (totalSeconds / 60) / 60;

        spentTime = timeFormatHours + " : " + timeFormatMinutes + " : " + timeFormatSeconds;
        return spentTime;
    }

}
