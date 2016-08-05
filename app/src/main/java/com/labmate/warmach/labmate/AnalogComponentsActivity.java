package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;

/**
 * Created by warmach on 3/8/16.
 */
public class AnalogComponentsActivity extends Activity {
    RelativeLayout opampRelativeLayout, timer555;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analog);
        bindViews();
        setListeners();
    }

    public void bindViews(){
        opampRelativeLayout = (RelativeLayout) findViewById(R.id.opamp_relativeLayout);
        timer555 = (RelativeLayout) findViewById(R.id.timer_555);
    }

    public void setListeners() {
        opampRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOpampPopup();
            }
        });
        timer555.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimer555();
            }
        });
    }

    public void showOpampPopup(){
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.opamp_layout, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button inverting = (Button) view.findViewById(R.id.inverting_button);
                Button nonInverting = (Button) view.findViewById(R.id.non_inverting_button);
                final Button downloadDatasheet = (Button) view.findViewById(R.id.opamp_datasheet_button);
                inverting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        navigateToOpampConfig("inverting");
                    }
                });
                nonInverting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        navigateToOpampConfig("non-inverting");
                    }
                });
                downloadDatasheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        downloadDatasheet();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void navigateToOpampConfig(String config){
        Intent intent = new Intent(AnalogComponentsActivity.this, OpampConfigurationActivity.class);
        intent.putExtra("config", config);
        startActivity(intent);
    }

    public void downloadDatasheet() {
        String url = "http://www.ti.com/lit/ds/symlink/lm741.pdf";
        String[] fileName = url.split("/");
        String name = fileName[fileName.length - 1];
        Log.v("File Name:", name);
        File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        File list[] = file.listFiles();
        int exists = 0;
        for(int i=0 ; i < list.length ; i++){
            if(list[i].getName().equals(name))
            {
                openFile(list[i]);
                exists = 1;
            }
        }
        if(exists == 1)
            Toast.makeText(getBaseContext(), "File Exists", Toast.LENGTH_SHORT).show();
        else if(isNetworkAvailable(this)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else
            Toast.makeText(getBaseContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
    }

    public void openFile(File file){
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getBaseContext(), "No PDF reader found..Please install one from the playstore", Toast.LENGTH_SHORT).show();
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

    public void setTimer555() {
        Intent intent = new Intent(AnalogComponentsActivity.this, Timer555Activity.class);
        startActivity(intent);
    }
}
