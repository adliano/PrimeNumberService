/*
* Author : Adriano Alves
* Date : Apr 17 2016
* Project Name : PrimeNumberService
* Objective : CS311D HW6
*             project to generate prime numbers
*             using service
*
 */

package com.cs311d.primenumbreservice;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PrimeNumberActivity extends Activity
{
    EditText edFrom, edTo;
    int intFrom, intTo, keyCounter;
    IntentFilter intentFilter;

    public static String ACTION_UPDATE_VIEW = "ACTION_UPDATE_VIEW";


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_primenumber_activity);

        edFrom = (EditText)findViewById(R.id.editText_from);
        edTo = (EditText)findViewById(R.id.editText_to);

        intentFilter = new IntentFilter(ACTION_UPDATE_VIEW);

    }
    /********** buttonSend *************/
    public void buttonSend(View view)
    {
        String strFrom = edFrom.getText().toString();
        String strTo = edTo.getText().toString();

        //if(edFrom.getText()!= null && edTo.getText()!= null)
        if(!strFrom.isEmpty() && !strTo.isEmpty())
        {
            keyCounter = 0;
            intFrom = Integer.parseInt(edFrom.getText().toString());
            intTo = Integer.parseInt(edTo.getText().toString());

            if(intFrom>=0 && intTo>0)
            {
                Intent callServiceIntent = new Intent(getBaseContext(),PrimeNumbersService.class);
                callServiceIntent.putExtra(PrimeNumbersService.KEY_NUMBER_FROM, intFrom);
                callServiceIntent.putExtra(PrimeNumbersService.KEY_NUMBER_TO, intTo);
                startService(callServiceIntent);
            }
            else
            {
                mkToast("INVALID RANGE NUMBERS");
                edFrom.setText(null);
                edFrom.setText(null);
            }
            //mkToast("WILL give Prime Numbers");
        }
        else mkToast("ENTER NUMBERS");
    }
    // method to call dialog that will stop service
    // if user click 10 times on a EditText TO it will call the dialog
    /*************** door ***************/
    public void door(View view)
    {
        keyCounter++;
        if(keyCounter==10)
        {
            FragmentManager manager = getFragmentManager();
            KillServiceDialog killer = new KillServiceDialog();
            killer.show(manager,"layout_dialog");
            keyCounter = 0;
        }
    }
    /******************** btServiceStatus ***************/
    public void btServiceStatus(View view)
    {
        if(isMyServiceRunning(PrimeNumbersService.class)) mkToast("RUNNING");
        else mkToast("DEAD");
    }
    /***************** isMyServiceRunning ************/
    private boolean isMyServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }
    /************** mkToast *************/
    public void mkToast(String msg)
    {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**** broadcast receiver *****/
    BroadcastReceiver UpdateViewReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            TextView tvTotalPrimes = (TextView)findViewById(R.id.textView_total_primes);
            String strTotalOfPrimes = intent.getStringExtra(PrimeNumbersService.KEY_TOTAL_OF_PRIME_NUMBERS);
            if(!strTotalOfPrimes.isEmpty())
            {
                tvTotalPrimes.setText(strTotalOfPrimes);
            }
        }
    };

    @Override
    protected void onResume()
    {
        super.onResume();
        // register broadcast receiver
        registerReceiver(UpdateViewReceiver,intentFilter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // unregister broadcast receiver if not using it
        unregisterReceiver(UpdateViewReceiver);
    }
}
