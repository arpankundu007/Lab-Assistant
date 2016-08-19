package com.labmate.warmach.labmate;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

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
        c2text = (TextView) findViewById(R.id.textView80);
        c3text = (TextView) findViewById(R.id.textView81);
        r1text = (TextView) findViewById(R.id.textView82);
        r5text = (TextView) findViewById(R.id.textView83);
        r6text = (TextView) findViewById(R.id.textView84);
        freq = (EditText) findViewById(R.id.sine_wave_freq_edittext);
        capUnits = (Spinner) findViewById(R.id.sine_wave_c1_spinner);
        freqUnits = (Spinner) findViewById(R.id.sine_wave_freq_spinner);
        calculateOutputButton = (Button) findViewById(R.id.calculate_sine_wave_button);
        scrollView = (ScrollView) findViewById(R.id.sine_wave_scrollview);
    }

    public void setListeners() {
        calculateOutputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOutput();
            }
        });
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


}
