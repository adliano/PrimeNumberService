package com.cs311d.primenumbreservice;
/*
* Author : Adriano Alves
* Date : Apr 17 2016
* Project Name : PrimeNumberService
* Objective : CS311D HW6 Dialog to kill service
*             project to generate prime numbers
*             using service
*
*/

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class KillServiceDialog extends DialogFragment
{
    final String PIN = "12345";
    final String PHRASE = "kill";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("STOP", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EditText edPin = (EditText)view.findViewById(R.id.editText_pin_number);
                        EditText edPhrase = (EditText)view.findViewById(R.id.editText_passphrase);
                        String strPin = edPin.getText().toString();
                        String strPhrase = edPhrase.getText().toString();
                        if(edPin.getText()!= null && edPhrase.getText()!= null)
                        {
                            if(strPin.equals(PIN) && strPhrase.equals(PHRASE))
                            {
                                Intent killServiceIntent = new Intent(getActivity(),PrimeNumbersService.class);
                                getActivity().stopService(killServiceIntent);
                                mkToast("SERVICE STOP");
                            }
                            else mkToast("WRONG PASSWORDS");
                        }
                        else mkToast("ENTER PASSWORDS");
                    }
                })
                .setNegativeButton(R.string.str_dialog_cancel_button, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });

        final AlertDialog dialog = builder.create();
        // called to show buttons , used to change the style of buttons
        dialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                Button btPositive = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                btPositive.setTextColor(Color.RED);

                Button btNegative = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                btNegative.setTextColor(Color.RED);
            }
        });

        return dialog;
    }
    /**************************************/
    @Override
    public void onStart()
    {
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.black);
        super.onStart();
    }

    /***************************/
    public void mkToast(String msg)
    {
        Toast.makeText(getActivity().getBaseContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
