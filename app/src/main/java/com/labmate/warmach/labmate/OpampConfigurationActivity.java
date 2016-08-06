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
 * Created by warmach on 3/8/16.
 */
public class OpampConfigurationActivity extends Activity {
    Intent intent;
    ImageView invertingImageView;
    Spinner configSpinner;
    Button gainButton, pinsButton;
    EditText r1EditText, r1PowerEditText, r2EditText, r2PowerEditText;
    TextView gainTextView, gainFormulaTextView;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opamp_config);
        intent = getIntent();
        bindViews();
        setListeners();
    }

    public void bindViews() {
        gainFormulaTextView = (TextView) findViewById(R.id.gain_formula_textview);
        gainTextView = (TextView) findViewById(R.id.gain_value_textview);
        invertingImageView = (ImageView) findViewById(R.id.config_type_imageview);
        configSpinner = (Spinner) findViewById(R.id.config_spinner);
        if(intent.getStringExtra("config").equals("inverting")) {
            setInverting();
        }
        else if(intent.getStringExtra("config").equals("non-inverting")) {
            setNonInverting();
        }
        gainButton = (Button) findViewById(R.id.gain_button);
        r1EditText = (EditText) findViewById(R.id.r1_editText);
        r1PowerEditText = (EditText) findViewById(R.id.r1_power_editText);
        r2EditText = (EditText) findViewById(R.id.r2_edittext);
        r2PowerEditText = (EditText) findViewById(R.id.r2_power_edittext);
        pinsButton = (Button) findViewById(R.id.opamp_pins_button);
        scrollView = (ScrollView) findViewById(R.id.opamp_scrollview);
    }

    public void setListeners() {
        configSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                    setInverting();
                else
                    setNonInverting();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setValidations()) {
                    if (configSpinner.getSelectedItemPosition() == 0)
                        calculateInvertingGain();
                    else
                        calculateNonInvertingGain();
                    ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getScrollY() + 1000).setDuration(1000).start();
                }
            }
        });
        pinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOpampPopup();
            }
        });
    }

    public void setInverting() {
        configSpinner.setSelection(0);
        invertingImageView.setBackgroundResource(R.drawable.inverting);
        gainTextView.setText("");
        gainFormulaTextView.setText("(-R2/R1)");
    }

    public void setNonInverting() {
        configSpinner.setSelection(1);
        invertingImageView.setBackgroundResource(R.drawable.non_inverting);
        gainTextView.setText("");
        gainFormulaTextView.setText("(1 + (R2/R1))");
    }

    public void calculateInvertingGain() {
        String r1 = r1EditText.getText().toString();
        String r1_power = r1PowerEditText.getText().toString();
        String r2 = r2EditText.getText().toString();
        String r2_power = r2PowerEditText.getText().toString();
        DecimalFormat df = new DecimalFormat("#.###");
        if(Float.parseFloat(r1) == 0)
            Toast.makeText(getBaseContext(), "R1 cannot be zero", Toast.LENGTH_LONG).show();
        else {
            float div = Float.parseFloat(r2) / Float.parseFloat(r1);
            int sub = (int) (Float.parseFloat(r2_power) - Float.parseFloat(r1_power));
            String gain = "";
            if (sub == 0)
                gain = "" + df.format(div);
            else
                gain = "" + df.format(div * Math.pow(10, sub));
            gainTextView.setText("- " + gain);
        }
    }

    public void calculateNonInvertingGain() {
        String r1 = r1EditText.getText().toString();
        String r1_power = r1PowerEditText.getText().toString();
        String r2 = r2EditText.getText().toString();
        String r2_power = r2PowerEditText.getText().toString();
        DecimalFormat df = new DecimalFormat("#.###");
        if(Float.parseFloat(r1) == 0)
            Toast.makeText(getBaseContext(), "R1 cannot be zero", Toast.LENGTH_LONG).show();
        else {
            float div = Float.parseFloat(r2) / Float.parseFloat(r1);
            int sub = (int) (Float.parseFloat(r2_power) - Float.parseFloat(r1_power));
            float temp = (float) (div * Math.pow(10, sub));
            float gain = 1 + temp;
            gainTextView.setText("" + df.format(gain));
        }
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
                        setInverting();
                    }
                });
                nonInverting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        setNonInverting();
                    }
                });
                downloadDatasheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utility.downloadDatasheet(OpampConfigurationActivity.this, "http://www.ti.com/lit/ds/symlink/lm741.pdf");
                    }
                });
            }
        });
        alertDialog.show();
    }

    public boolean setValidations() {
        if(r1EditText.getText().toString().length() == 0 || r1PowerEditText.getText().toString().length() == 0 ||
                r2EditText.getText().toString().length() == 0 || r2PowerEditText.getText().toString().length() == 0)
        {
            Toast.makeText(getBaseContext(), "Please enter the values", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
