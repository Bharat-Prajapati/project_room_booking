package com.example.apnaroom.utills;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static Uri saveBitmapAndGetUri(Context context, Bitmap bitmap){
        File file = new File(context.getCacheDir(), "profile_image.jpg");

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return FileProvider.getUriForFile(context, context.getPackageName()+".provider", file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
