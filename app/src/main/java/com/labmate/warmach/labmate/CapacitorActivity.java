package com.labmate.warmach.labmate;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by warmach on 23/7/16.
 */
public class CapacitorActivity extends Activity {
    EditText capacitorValueEditText, enteredCapacitanceEditText, enteredPowerEditText;
    Spinner capacitorUnitSpinner, capacitorToleranceSpinner, toleranceValueSpinner;
    Button calculateCapacitanceButton, showConversionButton, parseCapacitanceButton;
    TextView capacitanceValueTextView, capacitanceToleranceTextView, capacitorCodeTextView, capacitorToleranceTextView;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacitor);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        capacitorValueEditText = (EditText) findViewById(R.id.capacitance_code_edittext);
        capacitorToleranceSpinner = (Spinner) findViewById(R.id.capacitance_tolerance_spinner);
        toleranceValueSpinner = (Spinner) findViewById(R.id.capacitor_tolerance_values_spinner);
        calculateCapacitanceButton = (Button) findViewById(R.id.show_capacitance);
        capacitorUnitSpinner = (Spinner) findViewById(R.id.capacitance_unit_spinner);
        capacitanceValueTextView = (TextView) findViewById(R.id.capacitance_value);
        capacitanceToleranceTextView = (TextView) findViewById(R.id.capacitor_tolerance_value);
        showConversionButton = (Button) findViewById(R.id.capacitor_conversion_button);
        parseCapacitanceButton = (Button) findViewById(R.id.get_capacitor_code_button);
        enteredCapacitanceEditText = (EditText) findViewById(R.id.capacitance_entered_edittext);
        enteredPowerEditText = (EditText) findViewById(R.id.capacitance_power_edittext);
        capacitorCodeTextView = (TextView) findViewById(R.id.capacitor_code_textview);
        capacitorToleranceTextView = (TextView) findViewById(R.id.capacitor_tolerance_code_textview);
        scrollView = (ScrollView) findViewById(R.id.capacitance_scrollview);
    }

    public void setListeners() {
        calculateCapacitanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateCapacitance();
                setTolerance();
            }
        });
        capacitorToleranceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setTolerance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        showConversionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showCapacitanceConversion();
            }
        });
        parseCapacitanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseCapacitance();
                setToleranceCode();
            }
        });
    }

    public void calculateCapacitance() {
        if (capacitorValueEditText.getText().length() < 3 || Integer.parseInt(capacitorValueEditText.getText().toString()) < 100) {
            Toast.makeText(getBaseContext(), "PLease enter a valid 3 digit code", Toast.LENGTH_LONG).show();
            capacitanceValueTextView.setText("");
            capacitanceToleranceTextView.setText("");
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            capacitorValueEditText.startAnimation(shake);
        } else {
            capacitorUnitSpinner.setSelection(0);
            String capacitanceString = capacitorValueEditText.getText().toString();
            final int val_1 = Integer.parseInt(String.valueOf(capacitanceString.charAt(0)));
            final int val_2 = Integer.parseInt(String.valueOf(capacitanceString.charAt(1)));
            int val_3 = Integer.parseInt(String.valueOf(capacitanceString.charAt(2)));
            final int finalVal_ = val_3;
            String zeros = "";
            while (val_3 > 0) {
                zeros = zeros + "0";
                val_3--;
            }
            final String[] parsedCapacitance = new String[1];
            final Float capFloat = Float.parseFloat("" + val_1 + "" + val_2 + zeros);
            final DecimalFormat df = new DecimalFormat("#.#");
            df.setMaximumFractionDigits(3);
            Log.v("nano: ", "" + df.format(capFloat / 1000));
            Log.v("micro", "" + df.format(capFloat / 1000000));
            final String finalZeros = zeros;
            Log.v("code 3", "" + finalVal_);
            final String formattedCapacitance = "" + val_1 + val_2 + finalZeros;
            capacitorUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        if (formattedCapacitance.length() > 4)
                            parsedCapacitance[0] = "" + val_1 + val_2 + " x 10 ^ " + finalZeros.length();
                        else
                            parsedCapacitance[0] = formattedCapacitance;
                        capacitanceValueTextView.setText(parsedCapacitance[0]);
                    } else if (i == 1) {
                        if (finalVal_ != 9) {
                            parsedCapacitance[0] = df.format(capFloat / 1000);
                            capacitanceValueTextView.setText(parsedCapacitance[0]);
                        } else
                            capacitanceValueTextView.setText(formattedCapacitance.substring(0, formattedCapacitance.length() - 3));
                    } else if (i == 2) {
                        if (finalVal_ != 9) {
                            parsedCapacitance[0] = df.format(capFloat / 1000000);
                            capacitanceValueTextView.setText(parsedCapacitance[0]);
                        } else
                            capacitanceValueTextView.setText(formattedCapacitance.substring(0, formattedCapacitance.length() - 6));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            String formattedCap = "" + val_1 + val_2 + finalZeros;
            if (formattedCap.length() > 4)
                parsedCapacitance[0] = "" + val_1 + val_2 + " x 10 ^ " + finalZeros.length();
            else
                parsedCapacitance[0] = formattedCapacitance;
            capacitanceValueTextView.setText(parsedCapacitance[0]);
        }
    }

    public void setTolerance() {
        int position = capacitorToleranceSpinner.getSelectedItemPosition();
        String tolerance = "";
        switch (position) {
            case 0:
                tolerance = "";
                break;
            case 1:
                tolerance = "0.25 pF";
                break;
            case 2:
                tolerance = "0.5 pF";
                break;
            case 3:
                tolerance = "1 %";
                break;
            case 4:
                tolerance = "2 %";
                break;
            case 5:
                tolerance = "5 %";
                break;
            case 6:
                tolerance = "10 %";
                break;
            case 7:
                tolerance = "20 %";
                break;
            case 8:
                tolerance = "- 20% + 80%";
                break;
        }
        capacitanceToleranceTextView.setText(tolerance);
    }

    public void parseCapacitance() {
        String capacitance = enteredCapacitanceEditText.getText().toString();
        String power = enteredPowerEditText.getText().toString();
        Log.v("Test", String.valueOf(Float.parseFloat(capacitance) * Math.pow(10,Float.parseFloat(power))));
        if(Float.parseFloat(capacitance) * Math.pow(10,Float.parseFloat(power)) < 10)
            Toast.makeText(getBaseContext(), "Capacitance cannot be below 10pF", Toast.LENGTH_LONG).show();
        else {
            ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getScrollY() + 500).setDuration(1000).start();
            float resistanceFloat = Float.parseFloat(capacitance);
            float powerFloat = Float.parseFloat(power);
            String parsedCapacitance;
            int zerosLeft;
            if (powerFloat >= 3) {
                parsedCapacitance = new DecimalFormat("##").format((float) (resistanceFloat * Math.pow(10, 3)));
                zerosLeft = (int) (powerFloat - 3);
            } else {
                parsedCapacitance = new DecimalFormat("##").format((float) (resistanceFloat * Math.pow(10, powerFloat)));
                zerosLeft = 0;
            }
            Log.v("capacitance", parsedCapacitance);
            while (zerosLeft > 0) {
                parsedCapacitance = parsedCapacitance + "0";
                zerosLeft--;
            }
            parsedCapacitance = parsedCapacitance.replace(".", "");
            String cap_1 = String.valueOf(parsedCapacitance.charAt(0));
            String cap_2 = String.valueOf(parsedCapacitance.charAt(1));
            if ((parsedCapacitance.length() - 2) > 9)
                Toast.makeText(getBaseContext(), "Capacitance value out of range", Toast.LENGTH_LONG).show();
            else {
                String finalCapacitance = cap_1 + cap_2 + (parsedCapacitance.length() - 2);
                capacitorCodeTextView.setText(finalCapacitance);
            }
        }
    }

    public void setToleranceCode() {
        int position = toleranceValueSpinner.getSelectedItemPosition();
        String tolerance = "";
        switch (position) {
            case 0:
                tolerance = "C";
                break;
            case 1:
                tolerance = "D";
                break;
            case 2:
                tolerance = "F";
                break;
            case 3:
                tolerance = "G";
                break;
            case 4:
                tolerance = "J";
                break;
            case 5:
                tolerance = "K";
                break;
            case 6:
                tolerance = "M";
                break;
            case 7:
                tolerance = "Z";
                break;
        }
        capacitorToleranceTextView.setText(tolerance);
    }

    public void showCapacitanceConversion() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.capacitance_conversion, null);
        final AlertDialog conversionDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        conversionDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button close = (Button) view.findViewById(R.id.close_capacitance_conv_button);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        conversionDialog.dismiss();
                    }
                });
            }
        });
        conversionDialog.show();
    }
}
