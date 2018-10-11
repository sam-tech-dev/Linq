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

public class UtilFunctions {


    public static  boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


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



    public static Bitmap stringToBitmap(String imageString) {
        Bitmap imageBitmap = null;
        if (imageString != null && imageString.length() > 0) {
            byte[] image_data = Base64.decode(imageString, Base64.DEFAULT);
            imageBitmap = BitmapFactory.decodeByteArray(image_data, 0, image_data.length);
        }
        return imageBitmap;
    }

}
