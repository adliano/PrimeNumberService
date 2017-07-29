package com.cs311d.primenumbreservice;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;


public class PrimeNumbersService extends IntentService
{
    public static final String KEY_NUMBER_FROM = "from";
    public static final String KEY_NUMBER_TO = "to";
    public static final String KEY_TOTAL_OF_PRIME_NUMBERS = "total";

    /*** constructor ***/
    public PrimeNumbersService()
    {
        super("PrimeNumbersService");
    }
    /*** onStartCommand ***/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
    /*** onHandleIntent ***/
    // all job that should be done in a new thread must be place inside
    // this method
    @Override
    protected void onHandleIntent(Intent intent)
    {
        int intFrom = intent.getIntExtra(KEY_NUMBER_FROM,0);
        int intTo = intent.getIntExtra(KEY_NUMBER_TO,1);
        int intTotal = PrimeCounter.getTotalOfPrimes(intFrom, intTo);

        // create a broadcast that will update (respond) the TextView
        // start the Broadcast Intent to update the TextView via Broadcast
        Intent callBroadcastIntent = new Intent();
        callBroadcastIntent.setAction(PrimeNumberActivity.ACTION_UPDATE_VIEW);
        // send String over intent
        callBroadcastIntent.putExtra(KEY_TOTAL_OF_PRIME_NUMBERS, String.valueOf(intTotal));
        sendBroadcast(callBroadcastIntent);
    }
    /*** onCreate ***/
    @Override
    public void onCreate()
    {
        super.onCreate();
        mkToast("Service Created");
    }
    /************** mkToast *************/
    public void mkToast(String msg)
    {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
