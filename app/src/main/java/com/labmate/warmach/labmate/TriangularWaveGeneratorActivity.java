package com.labmate.warmach.labmate;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by warmach on 17/8/16.
 */
public class TriangularWaveGeneratorActivity extends Activity {
    ScrollView scrollView;
    RelativeLayout outputLayout;
    Button calculateComponentsButton;
    EditText freqEditText, cEditText;
    Spinner capPowerSpinner, freqPowerSpinner;
    TextView r1,r2,r3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangular_wave_generator);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        outputLayout = (RelativeLayout) findViewById(R.id.triangular_output_relative_layout);
        calculateComponentsButton = (Button) findViewById(R.id.triangular_wave_output_button);
        scrollView = (ScrollView) findViewById(R.id.triangular_wave_scrollview);
        freqEditText = (EditText) findViewById(R.id.triangular_wave_frequency_edittext);
        cEditText = (EditText) findViewById(R.id.triangular_wave_capacitor_edittext);
        capPowerSpinner = (Spinner) findViewById(R.id.triangular_wave_cap_units_spinner);
        freqPowerSpinner = (Spinner) findViewById(R.id.triangular_wave_freq_units_spinner);
        r1 = (TextView) findViewById(R.id.triangular_wave_r1);
        r2 = (TextView) findViewById(R.id.triangular_wave_r2);
        r3 = (TextView) findViewById(R.id.triangular_wave_r3);
        hideOutput();
    }

    public void setListeners() {
        calculateComponentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setValidations()) {
                    calculateOutput();
                    showOutput();
                }
            }
        });
    }

    public void calculateOutput() {
        String freqString = freqEditText.getText().toString();
        String capString = cEditText.getText().toString();
        if(capString.equals(""))
        {
            capString = "0.1";
            capPowerSpinner.setSelection(2);
            cEditText.setText("0.1");
        }
        float capPower, freqPower, capValue, freqValue;
        capValue = Float.parseFloat(capString);
        freqValue = Float.parseFloat(freqString);
        int capPowerInt = capPowerSpinner.getSelectedItemPosition();
        int freqPowerInt = freqPowerSpinner.getSelectedItemPosition();
        if(capPowerInt == 0)
            capPower = -12;
        else if(capPowerInt == 1)
            capPower = -9;
        else
            capPower = -6;
        if(freqPowerInt == 0)
            freqPower = 0;
        else if(freqPowerInt == 1)
            freqPower = 3;
        else
            freqPower = 6;
        float r1V,r2V,r3V;
        r1V = 2200;
        r2V = 10000;
        DecimalFormat df = new DecimalFormat("#.##");
        float r3_deno = (float) (4*capValue*freqValue*r1V*Math.pow(10, (capPower+freqPower)));
        r3V = r2V/r3_deno;
        String r1Output, r2Output;
        r1Output = df.format(r1V/1000) + " KOhms (Fixed)";
        r2Output = df.format(r2V/1000) + " KOhms (Fixed)";
        String r3Output;
        if(r3V>1000 && r3V<100000)
            r3Output = df.format(r3V/1000) + " KOhms";
        else if(r3V>=100000)
            r3Output = df.format(r3V/1000000) + " MOhms";
        else
            r3Output = df.format(r3V) + " Ohms";
        r1.setText(r1Output);
        r2.setText(r2Output);
        r3.setText(r3Output);
    }

    public void hideOutput(){
        outputLayout.setVisibility(View.GONE);
    }

    public void showOutput() {
        ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getScrollY() + 500).setDuration(1000).start();
        outputLayout.setVisibility(View.VISIBLE);
    }

    public boolean setValidations() {
        if(freqEditText.getText().toString().equals(""))
        {
            Toast.makeText(TriangularWaveGeneratorActivity.this, "Please enter a valid frequency", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
