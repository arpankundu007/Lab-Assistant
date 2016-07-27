package com.labmate.warmach.labmate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by warmach on 23/7/16.
 */
public class CapacitorActivity extends Activity {
    EditText capacitorValueEditText;
    Spinner capacitorUnitSpinner, capacitorToleranceSpinner;
    Button calculateCapacitanceButton;
    TextView capacitanceValueTextView, capacitanceToleranceTextView;

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
        calculateCapacitanceButton = (Button) findViewById(R.id.show_capacitance);
        capacitorUnitSpinner = (Spinner) findViewById(R.id.capacitance_unit_spinner);
        capacitanceValueTextView = (TextView) findViewById(R.id.capacitance_value);
        capacitanceToleranceTextView = (TextView) findViewById(R.id.capacitor_tolerance_value);
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
    }

    public void calculateCapacitance() {
        if (capacitorValueEditText.getText().length() < 3)
        {
            Toast.makeText(getBaseContext(), "PLease enter a valid 3 digit code", Toast.LENGTH_LONG).show();
            capacitanceValueTextView.setText("");
            capacitanceToleranceTextView.setText("");
        }
        else {
            capacitorUnitSpinner.setSelection(0);
            String capacitanceString = capacitorValueEditText.getText().toString();
            final int val_1 = Integer.parseInt(String.valueOf(capacitanceString.charAt(0)));
            final int val_2 = Integer.parseInt(String.valueOf(capacitanceString.charAt(1)));
            int val_3 = Integer.parseInt(String.valueOf(capacitanceString.charAt(2)));
            String zeros = "";
            while (val_3 > 0) {
                zeros = zeros + "0";
                val_3--;
            }
            final String[] parsedCapacitance = new String[1];
            final Float capFloat = Float.parseFloat("" + val_1 + "" + val_2 + zeros);
            final DecimalFormat df = new DecimalFormat("#.#");
            df.setMaximumFractionDigits(6);
            Log.v("nano: ", "" + df.format(capFloat / 1000));
            Log.v("micro", "" + df.format(capFloat / 1000000));
            final String finalZeros = zeros;
            capacitorUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        parsedCapacitance[0] = "" + val_1 + "" + val_2 + finalZeros;
                        capacitanceValueTextView.setText(parsedCapacitance[0]);
                    } else if (i == 1) {
                        parsedCapacitance[0] = df.format(capFloat / 1000);
                        capacitanceValueTextView.setText(parsedCapacitance[0]);
                    } else if (i == 2) {
                        parsedCapacitance[0] = df.format(capFloat / 1000000);
                        capacitanceValueTextView.setText(parsedCapacitance[0]);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            parsedCapacitance[0] = "" + val_1 + "" + val_2 + finalZeros;
            capacitanceValueTextView.setText(parsedCapacitance[0]);

        }
    }

    public void setTolerance() {
        int position = capacitorToleranceSpinner.getSelectedItemPosition();
        String tolerance = "";
        switch(position){
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
                tolerance = " - 20% + 80%";
                break;
        }
        capacitanceToleranceTextView.setText(tolerance);
    }
}
