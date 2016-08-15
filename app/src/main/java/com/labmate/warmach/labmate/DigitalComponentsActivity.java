package com.labmate.warmach.labmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by warmach on 15/8/16.
 */
public class DigitalComponentsActivity extends Activity {

    RelativeLayout gates, decoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        gates = (RelativeLayout) findViewById(R.id.not_gate);
        decoder = (RelativeLayout) findViewById(R.id.decoder_38_relativelayout);
    }

    public void setListeners() {
        gates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGates();
            }
        });

        decoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDecoder();
            }
        });
    }

    public void setGates() {
        Intent intent = new Intent(DigitalComponentsActivity.this, GatesActivity.class);
        startActivity(intent);
    }

    public void setDecoder() {
        Intent intent = new Intent(DigitalComponentsActivity.this, Decoder38Activity.class);
        startActivity(intent);
    }


}
