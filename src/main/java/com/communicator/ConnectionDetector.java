package com.communicator;

/**
 * Copyright (C) Greeno Tech Solutions Pvt. Ltd. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * @author SELLATHURAI
 * @Email sella.ragavan@greeno.in,
 * @Created 24 MAR 2016
 */

/**
 * @author SELLATHURAI
 * @Email sella.ragavan@greeno.in, 
 * @Created 24 MAR 2016
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector extends Activity {

    private Context _context;
    boolean connected = false;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    /**
     * Detecting network availability thorough out application
     */
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // we are connected to a network
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

        return connected;
    }

    /**
     * Alert view process thorough out application
     */
    @SuppressWarnings("deprecation")
    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
            }
        });

        alertDialog.show();

    }

}
