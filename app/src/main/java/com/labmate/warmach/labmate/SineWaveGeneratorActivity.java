package com.labmate.warmach.labmate;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by warmach on 17/8/16.
 */
public class SineWaveGeneratorActivity extends Activity {
    TextView c2, c3, r1, r5, r6;
    TextView c2text, c3text, r1text, r5text, r6text;
    EditText c1, freq;
    Spinner capUnits, freqUnits;
    Button calculateOutputButton;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sine_wave_generator);
        bindViews();
        hideOutput();
        setListeners();
    }

    public void bindViews() {
        c2 = (TextView) findViewById(R.id.sine_wave_c2_textview);
        c3 = (TextView) findViewById(R.id.sine_wave_c3_textview);
        r1 = (TextView) findViewById(R.id.sine_wave_r1_textview);
        r5 = (TextView) findViewById(R.id.sine_wave_r5_textview);
        r6 = (TextView) findViewById(R.id.sine_wave_r6_textview);
        c1 = (EditText) findViewById(R.id.sine_wave_c1_edittext);
        freq = (EditText) findViewById(R.id.sine_wave_freq_edittext);
        c2text = (TextView) findViewById(R.id.textView80);
        c3text = (TextView) findViewById(R.id.textView81);
        r1text = (TextView) findViewById(R.id.textView82);
        r5text = (TextView) findViewById(R.id.textView83);
        r6text = (TextView) findViewById(R.id.textView84);
        capUnits = (Spinner) findViewById(R.id.sine_wave_c1_spinner);
        freqUnits = (Spinner) findViewById(R.id.sine_wave_freq_spinner);
        freqUnits.setSelection(2);
        calculateOutputButton = (Button) findViewById(R.id.calculate_sine_wave_button);
        scrollView = (ScrollView) findViewById(R.id.sine_wave_scrollview);
    }

    public void setListeners() {
        calculateOutputButton.setOnClickListener(new View.OnClickListener() {
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
        String c1String = c1.getText().toString();
        if(c1String.equals(""))
        {
            c1.setText("0.001");
            c1String = "0.001";
            capUnits.setSelection(2);
        }
        String freqString = freq.getText().toString();
        int cap_pow = capUnits.getSelectedItemPosition();
        int freq_pow = freqUnits.getSelectedItemPosition();
        float capPowValue, freqPowValue;
        float c1Value = Float.parseFloat(c1String);
        float freqValue = Float.parseFloat(freqString);
        if(freq_pow == 0)
            freqPowValue = 0;
        else if(freq_pow == 1)
            freqPowValue = 3;
        else
            freqPowValue = 6;
        if(cap_pow == 0)
            capPowValue = -12;
        else if (cap_pow == 1)
            capPowValue = -9;
        else
            capPowValue = -6;
        DecimalFormat df = new DecimalFormat("#.#");
        DecimalFormat df2 = new DecimalFormat("#.#####");
        String c2Value = df2.format(c1Value) + " " + capUnits.getSelectedItem().toString();
        c2.setText(c2Value);
        String c3Value = df2.format(2 * c1Value) + " " + capUnits.getSelectedItem().toString();
        c3.setText(c3Value);
        float r1_deno = (float) (0.693 * c1Value * 2 * freqValue);
        float r1Float = (1/r1_deno);
        float r5_deno = (float) (8.8856 * freqValue * c1Value);
        float r5Float = 1/r5_deno;
        String resString1;
        if(r1Float * Math.pow(10,  (-freqPowValue - capPowValue)) > 1000)
            resString1 = df.format((r1Float * Math.pow(10,  (-freqPowValue - capPowValue)))/1000) + " KOhms";
        else
            resString1 = df.format(r1Float * Math.pow(10,  (-freqPowValue - capPowValue))) + " Ohms";
        r1.setText(resString1);
        String resString;
        if(r5Float * Math.pow(10,  (-freqPowValue - capPowValue)) > 1000)
            resString = df.format((r5Float * Math.pow(10,  (-freqPowValue - capPowValue)))/1000) + " KOhms";
        else
            resString = df.format(r5Float * Math.pow(10,  (-freqPowValue - capPowValue))) + " Ohms";
        r5.setText(resString);
        r6.setText(resString);

    }

    public void hideOutput() {
        c2.setVisibility(View.GONE);
        c3.setVisibility(View.GONE);
        r1.setVisibility(View.GONE);
        r5.setVisibility(View.GONE);
        r6.setVisibility(View.GONE);
        c2text.setVisibility(View.GONE);
        c3text.setVisibility(View.GONE);
        r1text.setVisibility(View.GONE);
        r5text.setVisibility(View.GONE);
        r6text.setVisibility(View.GONE);
    }

    public void showOutput() {
        ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getScrollY() + 500).setDuration(1000).start();
        c2.setVisibility(View.VISIBLE);
        c3.setVisibility(View.VISIBLE);
        r1.setVisibility(View.VISIBLE);
        r5.setVisibility(View.VISIBLE);
        r6.setVisibility(View.VISIBLE);
        c2text.setVisibility(View.VISIBLE);
        c3text.setVisibility(View.VISIBLE);
        r1text.setVisibility(View.VISIBLE);
        r5text.setVisibility(View.VISIBLE);
        r6text.setVisibility(View.VISIBLE);
    }

    public boolean setValidations() {
        if(freq.getText().toString().equals("")) {
            Toast.makeText(SineWaveGeneratorActivity.this, "Please enter a valid frequency", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
