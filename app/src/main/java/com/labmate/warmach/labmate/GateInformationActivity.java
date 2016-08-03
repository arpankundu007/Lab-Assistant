package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by warmach on 30/7/16.
 */
public class GateInformationActivity extends Activity {

    TextView gateNameTextView;
    ImageView gateTruthImageView, gateImageView;
    Intent intent;
    Button showPinOutButton;
    int[] gate_images = {R.drawable.and_gate, R.drawable.or_gate, R.drawable.xor_gate, R.drawable.nand_gate, R.drawable.nor_gate, R.drawable.xnor_gate, R.drawable.not_gate, R.drawable.timer_555};
    int[] truth_images = {R.drawable.truth_and, R.drawable.truth_or,
            R.drawable.truth_xor,R.drawable.truth_nand,
            R.drawable.truth_nor, R.drawable.truth_xnor,
            R.drawable.truth_not, R.drawable.no_timer};
    int[] ttl_pinouts = {R.drawable.ttl_and, R.drawable.ttl_or, R.drawable.ttl_xor, R.drawable.ttl_nand, R.drawable.ttl_nor, R.drawable.ttl_xnor, R.drawable.ttl_not, R.drawable.timer_pins};
    int[] cmos_pinouts = {R.drawable.cmos_and, R.drawable.cmos_or, R.drawable.cmos_xor, R.drawable.cmos_nand, R.drawable.cmos_nor, R.drawable.cmos_xnor, R.drawable.ttl_not};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_gate_information);
        intent = getIntent();
        bindViews();
        setGateName();
        setListeners();
    }

    public void bindViews() {
        gateNameTextView = (TextView) findViewById(R.id.gate_name_textview);
        gateTruthImageView = (ImageView) findViewById(R.id.gate_truth_table_imageview);
        gateImageView = (ImageView) findViewById(R.id.gate_imageview);
        showPinOutButton = (Button) findViewById(R.id.show_pin_diagram_button);
    }

    public void setListeners() {
        showPinOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPinTypePopup();
            }
        });
    }

    public void setGateName() {
        String gateType = intent.getStringExtra("gate_type");
        switch (gateType){
            case "and":
                gateNameTextView.setText("AND Gate");
                gateImageView.setBackgroundResource(gate_images[0]);
                gateTruthImageView.setBackgroundResource(truth_images[0]);
                break;
            case "or":
                gateNameTextView.setText("OR Gate");
                gateImageView.setBackgroundResource(gate_images[1]);
                gateTruthImageView.setBackgroundResource(truth_images[1]);
                break;
            case "xor":
                gateNameTextView.setText("XOR Gate");
                gateImageView.setBackgroundResource(gate_images[2]);
                gateTruthImageView.setBackgroundResource(truth_images[2]);
                break;
            case "nand":
                gateNameTextView.setText("NAND Gate");
                gateImageView.setBackgroundResource(gate_images[3]);
                gateTruthImageView.setBackgroundResource(truth_images[3]);
                break;
            case "nor":
                gateNameTextView.setText("NOR Gate");
                gateImageView.setBackgroundResource(gate_images[4]);
                gateTruthImageView.setBackgroundResource(truth_images[4]);
                break;
            case "xnor":
                gateNameTextView.setText("XNOR Gate");
                gateImageView.setBackgroundResource(gate_images[5]);
                gateTruthImageView.setBackgroundResource(truth_images[5]);
                break;
            case "not":
                gateNameTextView.setText("NOT Gate");
                gateImageView.setBackgroundResource(gate_images[6]);
                gateTruthImageView.setBackgroundResource(truth_images[6]);
                break;
            case "timer_555":
                gateNameTextView.setText("555 Timer");
                gateImageView.setBackgroundResource(gate_images[7]);
                gateTruthImageView.setBackgroundResource(truth_images[7]);
                break;
        }
    }

    public void showPinTypePopup() {
        if (getIntent().getStringExtra("gate_type").equals("timer_555"))
            showTtlPopup();
        else {
            LayoutInflater inflater = getLayoutInflater();
            final View view = inflater.inflate(R.layout.pin_out_type_popup, null);
            final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    final RadioButton ttl = (RadioButton) view.findViewById(R.id.ttl_radioButton);
                    final RadioButton cmos = (RadioButton) view.findViewById(R.id.cmos_radioButton);
                    Button confirm = (Button) view.findViewById(R.id.chip_type_button);

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ttl.isChecked()) {
                                alertDialog.dismiss();
                                showTtlPopup();
                            } else if (cmos.isChecked()) {
                                alertDialog.dismiss();
                                showCmosPopup();
                            } else
                                Toast.makeText(GateInformationActivity.this, "Please select an option", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
            alertDialog.show();
        }

    }

    public void showTtlPopup(){
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.pinout_popup, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                TextView chipname = (TextView) view.findViewById(R.id.chip_name_textview);
                ImageView chipPinImage = (ImageView) view.findViewById(R.id.pin_imageview);
                Button close = (Button) view.findViewById(R.id.close_chip_button);
                Button datasheet = (Button) view.findViewById(R.id.datasheet_button);
                setTtlDetails(chipname, chipPinImage);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                datasheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        downloadTtlDatasheet();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void setTtlDetails(TextView chipName, ImageView chipPinImage) {
        String chipType = intent.getStringExtra("gate_type");
        switch (chipType){
            case "and":
                chipName.setText("74LS08");
                chipPinImage.setBackgroundResource(ttl_pinouts[0]);
                break;
            case "or":
                chipName.setText("74LS32");
                chipPinImage.setBackgroundResource(ttl_pinouts[1]);
                break;
            case "xor":
                chipName.setText("74LS86");
                chipPinImage.setBackgroundResource(ttl_pinouts[2]);
                break;
            case "nand":
                chipName.setText("74LS00");
                chipPinImage.setBackgroundResource(ttl_pinouts[3]);
                break;
            case "nor":
                chipName.setText("74LS02");
                chipPinImage.setBackgroundResource(ttl_pinouts[4]);
                break;
            case "xnor":
                chipName.setText("74LS266");
                chipPinImage.setBackgroundResource(ttl_pinouts[5]);
                break;
            case "not":
                chipName.setText("74LS04");
                chipPinImage.setBackgroundResource(ttl_pinouts[6]);
                break;
            case "timer_555":
                chipName.setText("LM555 Timer");
                chipPinImage.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
                chipPinImage.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
                chipPinImage.setBackgroundResource(ttl_pinouts[7]);
                break;
        }
    }

    public void downloadTtlDatasheet() {
        String chipType = intent.getStringExtra("gate_type");
        String url = "";
        switch (chipType) {
            case "and":
                url = "https://www.sonoma.edu/users/m/marivani/datasheets/74ls_series/7408.pdf";
                break;
            case "or":
                url = "https://www.fairchildsemi.com/datasheets/DM/DM74ALS32.pdf";
                break;
            case "xor":
                url = "http://www.elexp.com/PDFs/1074LS86.pdf";
                break;
            case "nand":
                url = "https://www.fairchildsemi.com/datasheets/DM/DM74ALS00A.pdf";
                break;
            case "nor":
                url = "https://www.fairchildsemi.com/datasheets/DM/DM74ALS02.pdf";
                break;
            case "xnor":
                url = "http://pdf.datasheetcatalog.com/datasheets/50/375549_DS.pdf";
                break;
            case "not":
                url = "https://www.fairchildsemi.com/datasheets/DM/DM74ALS04B.pdf";
                break;
            case "timer_555":
                url = "https://www.fairchildsemi.com/datasheets/lm/lm555.pdf";
                break;
        }
        try {
            downloadFile(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void showCmosPopup() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.pinout_popup, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                TextView chipname = (TextView) view.findViewById(R.id.chip_name_textview);
                ImageView chipPinImage = (ImageView) view.findViewById(R.id.pin_imageview);
                Button close = (Button) view.findViewById(R.id.close_chip_button);
                Button download = (Button) view.findViewById(R.id.datasheet_button);
                setCmosDetails(chipname, chipPinImage);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        downloadCmosDatasheet();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void setCmosDetails(TextView chipName, ImageView chipPinImage){
        String chipType = intent.getStringExtra("gate_type");
        switch (chipType) {
            case "and":
                chipName.setText("CD4081");
                chipPinImage.setBackgroundResource(cmos_pinouts[0]);
                break;
            case "or":
                chipName.setText("CD4071");
                chipPinImage.setBackgroundResource(cmos_pinouts[1]);
                break;
            case "xor":
                chipName.setText("CD4070");
                chipPinImage.setBackgroundResource(cmos_pinouts[2]);
                break;
            case "nand":
                chipName.setText("CD4011");
                chipPinImage.setBackgroundResource(cmos_pinouts[3]);
                break;
            case "nor":
                chipName.setText("CD4001");
                chipPinImage.setBackgroundResource(cmos_pinouts[4]);
                break;
            case "xnor":
                chipName.setText("CD4077");
                chipPinImage.setBackgroundResource(cmos_pinouts[5]);
                break;
            case "not":
                chipName.setText("CD4069");
                chipPinImage.setBackgroundResource(cmos_pinouts[6]);
                break;
        }
    }

    public void downloadCmosDatasheet() {
        String chipType = intent.getStringExtra("gate_type");
        String url = "";
        switch (chipType) {
            case "and":
                url = "http://www.pcbheaven.com/datasheet/cd4081.pdf";
                break;
            case "or":
                url = "http://www.pcbheaven.com/datasheet/cd4071_72_75.pdf";
                break;
            case "xor":
                url = "http://www-3.unipv.it/lde/strumentazione_componentistica/datasheet/4070.pdf";
                break;
            case "nand":
                url = "http://www.ti.com/lit/ds/symlink/cd4011b.pdf";
                break;
            case "nor":
                url = "https://www.elektronik-kompendium.de/public/schaerer/FILES/cd4001b_cd4011b.pdf";
                break;
            case "xnor":
                url = "http://www.ti.com/lit/ds/symlink/cd4077b.pdf";
                break;
            case "not":
                url = "https://www.fairchildsemi.com/datasheets/CD/CD4069UBC.pdf";
                break;
        }
        try {
            downloadFile(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String url) throws MalformedURLException {
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
            Toast.makeText(GateInformationActivity.this, "File Exists", Toast.LENGTH_SHORT).show();
        else if(isNetworkAvailable(this)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else
            Toast.makeText(GateInformationActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
    }

    public void openFile(File file){
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GateInformationActivity.this, "No PDF reader found..Please install one from the playstore", Toast.LENGTH_SHORT).show();
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
