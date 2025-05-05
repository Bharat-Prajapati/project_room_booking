package com.example.apnaroom.utills;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class AndroidUtils {
    private static final String TAG = "ApnaRoomTag";

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void logError(String message){
        Log.e(TAG, message);
    }

    public static void customTag(String customTag, String message){
        Log.d(customTag, message);
    }
}
