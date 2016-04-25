/**
 * @author sella.R
 * @version 1, Rel 1, 02Nov2014
 */
package com.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GenericAction {


    public static String USER_ID = "0";


    public static String USERNAME = "";
    public static String MOBILE = "";
    public static List<String> productId = new ArrayList<String>();


    public static String convertDecimalFormat(double amount) {

        Double totalAmounts = Double.valueOf(amount);
        DecimalFormat dec = new DecimalFormat();
        dec.setMinimumFractionDigits(2);
        String credits = dec.format(totalAmounts);
        return credits;

    }

    public static String convertHexaToString(String getString) {

        StringBuilder stringBuilder = new StringBuilder();
        String tempData = "";
        for (int j = 0; j < getString.length(); j += 4) {
            tempData = getString.substring(j, j + 4);
            stringBuilder.append((char) Integer.parseInt(tempData, 16));
        }
        String convertedString = stringBuilder.toString();

        return convertedString;

    }

    public static String convertStringToHexadecimal(String hexaString) {

        String convertingString = "";
        for (int i = 0; i < hexaString.length(); i++) {
            convertingString = convertingString
                    + String.format("%04x", (int) hexaString.charAt(i));
        }

        return convertingString;

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

    public static String doubleToString(Double y) {
        if (y != null)
            return String.valueOf(y);

        return null;
    }

    public static Double stringToDouble(String x) {
        if (x != null)
            return Double.parseDouble(x);

        return null;
    }
}
