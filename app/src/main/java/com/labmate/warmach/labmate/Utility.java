package com.labmate.warmach.labmate;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by warmach on 6/8/16.
 */
public class Utility {

    public static void downloadDatasheet(Context context, String url) {
        String[] fileName = url.split("/");
        String name = fileName[fileName.length - 1];
        Log.v("File Name:", name);
        File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        File list[] = file.listFiles();
        int exists = 0;
        for(int i=0 ; i < list.length ; i++){
            if(list[i].getName().equals(name))
            {
                openFile(context, list[i]);
                exists = 1;
            }
        }
        if(exists == 1)
            Toast.makeText(context, "File Exists", Toast.LENGTH_SHORT).show();
        else if(isNetworkAvailable(context)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
        else
            Toast.makeText(context, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
    }

    public static void openFile(Context context, File file){
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open File");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF reader found..Please install one from the playstore", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            }
            else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }
}
