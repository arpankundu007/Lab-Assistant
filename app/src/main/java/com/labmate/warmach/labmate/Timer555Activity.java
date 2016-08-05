package com.labmate.warmach.labmate;

import android.animation.ObjectAnimator;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by warmach on 5/8/16.
 */
public class Timer555Activity extends Activity {
    Spinner timerModeSPinner, capacitorUnitsSpinner, r1UnitsSpinner, r2UnitsSpinner;
    ImageView timerModeImageView;
    TextView r2TextView, textView, freqTextView, dutyTextView, tHighTextView, tLowTextView;
    EditText r1EditText, r1PowerEditText, r2EditText, r2PowerEditText, cEditText, cPowerEditText;
    Button timerOutputButton, timerPinsButton;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_555_details);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        timerModeSPinner = (Spinner) findViewById(R.id.timer_mode_spinner);
        timerModeImageView = (ImageView) findViewById(R.id.timer_mode_imageview);
        r2TextView = (TextView) findViewById(R.id.timer_r2_textview);
        textView = (TextView) findViewById(R.id.textView56);
        r1EditText = (EditText) findViewById(R.id.timer_r1_edittext);
        r1PowerEditText = (EditText) findViewById(R.id.timer_r1_power_edittext);
        r2EditText = (EditText) findViewById(R.id.timer_r2_edittext);
        r2PowerEditText = (EditText) findViewById(R.id.timer_r2_power_edittext);
        cEditText = (EditText) findViewById(R.id.timer_capacitor_edittext);
        cPowerEditText = (EditText) findViewById(R.id.timer_capacitor_power_edittext);
        timerOutputButton = (Button) findViewById(R.id.timer_output_button);
        timerPinsButton = (Button) findViewById(R.id.timer_555_pinouts_button);
        scrollView = (ScrollView) findViewById(R.id.timer_scrollview);
        freqTextView = (TextView) findViewById(R.id.timer_frequency_textview);
        dutyTextView = (TextView) findViewById(R.id.timer_duty_cycle_textview);
        tHighTextView = (TextView) findViewById(R.id.t_high_textview);
        tLowTextView = (TextView) findViewById(R.id.t_low_textview);
        capacitorUnitsSpinner = (Spinner) findViewById(R.id.timer_capacitor_units_spinner);
        r1UnitsSpinner = (Spinner) findViewById(R.id.timer_r1_units_spinner);
        r2UnitsSpinner = (Spinner) findViewById(R.id.timer_r2_units_spinner);
    }

    public void setListeners() {
        timerModeSPinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if (i == 0)
                    setAstableMode();
                else
                    setMonoStableMode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        timerOutputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setValidations()) {
                    if (timerModeSPinner.getSelectedItemPosition() == 0)
                        setAstableModeCalculation();
                    else
                        setMonoStableModeCalculation();
                    ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getScrollY() + 1000).setDuration(1000).start();
                }
            }
        });

        timerPinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimerPins();
            }
        });
        r1EditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getScrollY() + 1000).setDuration(1000).start();
                return false;
            }
        });
    }

    public void setAstableMode() {
        timerModeImageView.setBackgroundResource(R.drawable.timer_555_config);
        showR2();
    }

    public void setMonoStableMode() {
        timerModeImageView.setBackgroundResource(R.drawable.timer_555_monostable);
        hideR2();

    }

    public void hideR2() {
        r2TextView.setVisibility(View.GONE);
        r2UnitsSpinner.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        r2EditText.setVisibility(View.GONE);
        r2PowerEditText.setVisibility(View.GONE);
    }

    public void showR2() {
        r2TextView.setVisibility(View.VISIBLE);
        r2UnitsSpinner.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        r2EditText.setVisibility(View.VISIBLE);
        r2PowerEditText.setVisibility(View.VISIBLE);
    }

    public void showTimerPins() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.pinout_popup, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                TextView chipname = (TextView) view.findViewById(R.id.chip_name_textview);
                ImageView chipImage = (ImageView) view.findViewById(R.id.pin_imageview);
                Button download = (Button) view.findViewById(R.id.datasheet_button);
                Button close = (Button) view.findViewById(R.id.close_chip_button);
                chipname.setText("NE555 Timer");
                chipImage.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
                chipImage.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
                chipImage.setBackgroundResource(R.drawable.timer_pins);
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        downloadDatasheet();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void setAstableModeCalculation() {
        String r1 = r1EditText.getText().toString();
        String r1_power = r1PowerEditText.getText().toString();
        String r2 = r2EditText.getText().toString();
        String r2_power = r2PowerEditText.getText().toString();
        String c = cEditText.getText().toString();
        String c_power = cPowerEditText.getText().toString();
        String formattedHigh, formattedLow;
        float r1Value, r2Value;
        if(r1UnitsSpinner.getSelectedItemPosition() == 0)
            r1Value = (float) (Float.parseFloat(r1) * Math.pow(10, Float.parseFloat(r1_power)));
        else if (r1UnitsSpinner.getSelectedItemPosition() == 1)
            r1Value = (float) (Float.parseFloat(r1) * Math.pow(10, (Float.parseFloat(r1_power) + 3)));
        else
            r1Value = (float) (Float.parseFloat(r1) * Math.pow(10, (Float.parseFloat(r1_power) + 6)));

        if(r2UnitsSpinner.getSelectedItemPosition() == 0)
            r2Value = (float) (Float.parseFloat(r2) * Math.pow(10, Float.parseFloat(r2_power)));
        else if(r2UnitsSpinner.getSelectedItemPosition() == 1)
            r2Value = (float) (Float.parseFloat(r2) * Math.pow(10, (Float.parseFloat(r2_power) + 3)));
        else
            r2Value = (float) (Float.parseFloat(r2) * Math.pow(10, (Float.parseFloat(r2_power) + 6)));
        float cValue;
        if(capacitorUnitsSpinner.getSelectedItemPosition() == 0)
            cValue = (float) (Float.parseFloat(c) * Math.pow(10, (Float.parseFloat(c_power)) - 12));
        else if(capacitorUnitsSpinner.getSelectedItemPosition() == 1)
            cValue = (float) (Float.parseFloat(c) * Math.pow(10, (Float.parseFloat(c_power)) - 9));
        else
            cValue = (float) (Float.parseFloat(c) * Math.pow(10, (Float.parseFloat(c_power)) - 6));
        DecimalFormat df = new DecimalFormat("#.###");
        float sum = r1Value +(2 * r2Value);
        float deno = sum * cValue;
        float div = (float) (1.44/deno);
        float duty = (r1Value + r2Value)/sum;
        float t_high = (float) (0.693 * (r1Value + r2Value) * cValue);
        float t_low = (float) (0.693 * r2Value * cValue);

        if((t_high * Math.pow(10,3)) > 1000)
            formattedHigh = df.format(t_high) + " s";
        else if((t_high * Math.pow(10,3)) > 1 && (t_high * Math.pow(10,3)) < 1000)
            formattedHigh = df.format(t_high * Math.pow(10,3)) + " ms";
        else
            formattedHigh = df.format(t_high * Math.pow(10,6)) + " μs";

        if((t_low * Math.pow(10,3)) > 1000)
            formattedLow = df.format(t_low) + " s";
        else if((t_low * Math.pow(10,3)) > 1  && (t_low * Math.pow(10,3)) < 1000)
            formattedLow = df.format(t_low * Math.pow(10,3)) + " ms";
        else
            formattedLow = df.format(t_low * Math.pow(10,6)) + " μs";
        String dutyCycle = df.format(duty * 100) + " %";
        String formatted_div;
        if(div > 1000)
            formatted_div = df.format(div / 1000) + " KHz";
        else if(div > 1000000)
            formatted_div = df.format(div/1000000) + " MHz";
        else
            formatted_div = df.format(div) + " Hz";
        freqTextView.setText(formatted_div);
        dutyTextView.setText(dutyCycle);
        tHighTextView.setText(formattedHigh);
        tLowTextView.setText(formattedLow);

    }

    public void setMonoStableModeCalculation() {

    }

    public void downloadDatasheet(){
        String url = "http://www.ti.com/lit/ds/symlink/ne555.pdf";
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

    public boolean setValidations() {
        if(r1EditText.getText().toString().length() == 0 || r2EditText.getText().toString().length() == 0 || cEditText.getText().toString().length() == 0  ) {
            Toast.makeText(Timer555Activity.this, "Empty values not allowed", Toast.LENGTH_SHORT).show();
            freqTextView.setText("");
            dutyTextView.setText("");
            tHighTextView.setText("");
            tLowTextView.setText("");
            return false;
        }
        else if(r1PowerEditText.getText().toString().length() == 0 || cPowerEditText.getText().toString().length() == 0 || cPowerEditText.getText().toString().length() == 0) {
            r1PowerEditText.setText("0");
            r2PowerEditText.setText("0");
            cPowerEditText.setText("0");
            return true;
        }
        return true;
    }
}
