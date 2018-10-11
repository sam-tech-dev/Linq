package com.i2e1.linq.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilFunctions {


    /**
     * function to check  whether neywork connections available or not ?
     * @param context Context to get service to check internet connectivity
     * @return Boolean
     */
    public static  boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * function to convert image bitmap to String format
     * @param bitmap Bitmap to be converted
     * @return String ..image data string
     */
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] bytedata = stream.toByteArray();
        String encodedImageString = Base64.encodeToString(bytedata, Base64.DEFAULT);
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("az", "exception in bitmap to string " + e.toString());
        }
        return encodedImageString;
    }


    /**
     * function to convert string to Bitmap
     * @param imageString
     * @return
     */
    public static Bitmap stringToBitmap(String imageString) {
        Bitmap imageBitmap = null;
        if (imageString != null && imageString.length() > 0) {
            byte[] image_data = Base64.decode(imageString, Base64.DEFAULT);
            imageBitmap = BitmapFactory.decodeByteArray(image_data, 0, image_data.length);
        }
        return imageBitmap;
    }


    /**
     * function convert phone number in clean format by removing special characters and giving space betweeen them
     * @param phoneNumberString
     * @return
     */
    public static String getPhoneNumberFormat(String phoneNumberString) {
        String phone=phoneNumberString.replace("(", "").replace(")","").replace("-","");
        StringBuilder builder= new StringBuilder(phone);
        builder.insert(3," ");
        builder.insert(7," ");
        return builder.toString();
    }


    /**
     * function to covert datetime string into 12/09/2018 date format
     * @param dateString datetime string to be converted
     * @return
     */
    public static String convertDateTimeToDateFormat(String dateString) {
        String dateFormat = "";
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            final Date dateObj = sdf.parse(dateString);
            dateFormat = new SimpleDateFormat("dd").format(dateObj)+"/"+new SimpleDateFormat("MM").format(dateObj)+"/"+new SimpleDateFormat("yyyy").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

}
