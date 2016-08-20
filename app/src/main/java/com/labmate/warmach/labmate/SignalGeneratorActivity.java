package com.labmate.warmach.labmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by warmach on 17/8/16.
 */
public class SignalGeneratorActivity extends Activity {
    RelativeLayout sine, triangular, square;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_generators);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        sine = (RelativeLayout) findViewById(R.id.sine);
        triangular = (RelativeLayout) findViewById(R.id.triangular);
        square = (RelativeLayout) findViewById(R.id.square);
    }

    public void setListeners() {
        sine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSineWaveGenerator();
            }
        });
        triangular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToTriangularWaveGenerator();
            }
        });
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSquareWaveGenerator();
            }
        });
    }

    public void navigateToSineWaveGenerator() {
        Intent intent = new Intent(SignalGeneratorActivity.this, SineWaveGeneratorActivity.class);
        startActivity(intent);
    }

    public void navigateToTriangularWaveGenerator() {
        Intent intent = new Intent(SignalGeneratorActivity.this, TriangularWaveGeneratorActivity.class);
        startActivity(intent);
    }

    public void navigateToSquareWaveGenerator() {
        Intent intent = new Intent(SignalGeneratorActivity.this, Timer555Activity.class);
        Toast.makeText(SignalGeneratorActivity.this, "555 Timer can be used as a square wave generator", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
