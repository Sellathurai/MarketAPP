package com.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

/*
 * Created by Ramya.D on 17-06-2015.
 */
public class CommonClass {

    Context mContext;

    public CommonClass() {

    }

    public CommonClass(Context mContext) {
        this.mContext = mContext;
    }

    /* Convert a text of String to Hexa */
    public static String convertStringToHexadecimal(String hexaString) {

        String convertingString = "";
        for (int i = 0; i < hexaString.length(); i++) {
            convertingString = convertingString
                    + String.format("%04x", (int) hexaString.charAt(i));
        }
        return convertingString;
    }


    /**
     * This method is used to display the Toast message through the application.
     */
    public void displayToast(String message) {
        Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Detecting network availability thorough out application
     */
    public boolean isInternetConnected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        return connected;
    }

    /**
     * This method checks if a String contains only numbers
     */
    public static boolean containsOnlyNumbers(String str) {

        //It can't contain only numbers if it's null or empty...
        if (str == null || str.length() == 0)
            return false;

        for (int i = 0; i < str.length(); i++) {
            //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    /* Validates whether mobile number is a valid Indian mobile number */
    public boolean checkIndianNumber(String mobileNumber) {
        String firstDigit;
        if ((mobileNumber != null) && (!mobileNumber.equals("")) && (mobileNumber.length() > 0)) {
            firstDigit = mobileNumber.substring(0, 1);
            if (Integer.parseInt(firstDigit) < 7)
                return false;
        }
        return true;
    }

    /**
     * To check the language of the text
     */
    public String checkLanguage(String hexaString) {

        String language = "";
        for (int i = 0; i < hexaString.length(); i++) {
            int cv = (int) hexaString.charAt(i);
            if (cv > 255) {
                language = "tamil";
                break;
            } else {
                language = "english";
            }
        }
        return language;
    }

    /* Convert a text of Hexa to String */
    public String convertHexaToString(String getString) {
        String convertedString = "";
        if (getString != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String tempData;
            for (int j = 0; j < getString.length(); j += 4) {
                tempData = getString.substring(j, j + 4);
                stringBuilder.append((char) Integer.parseInt(tempData, 16));
            }
            convertedString = stringBuilder.toString();
        }
        return convertedString;

    }


    /* clear all static objects */


}
