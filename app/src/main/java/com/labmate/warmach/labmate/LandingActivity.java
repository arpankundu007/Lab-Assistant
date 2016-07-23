package com.labmate.warmach.labmate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LandingActivity extends Activity {

    ImageButton resistorImageButton, capacitorImageButton, inductorImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setListeners();
    }

    public void bindViews(){
        resistorImageButton = (ImageButton) findViewById(R.id.resistorButton);
        capacitorImageButton = (ImageButton) findViewById(R.id.capacitorButton);
        inductorImageButton = (ImageButton) findViewById(R.id.inductorButton);
    }

    public void setListeners() {
        resistorImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, ResistorActivity.class);
                startActivity(intent);
            }
        });
        capacitorImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, CapacitorActivity.class);
                startActivity(intent);
            }
        });
        inductorImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, InductorActivity.class);
                startActivity(intent);
            }
        });

    }
}
