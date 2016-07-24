package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
                showBandPopup();

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

    public void showBandPopup() {
        LayoutInflater inflater = getLayoutInflater();
        final View type_view = inflater.inflate(R.layout.resistor_bands, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).
                setView(type_view).setCancelable(true).create();
        final RadioButton bands4 = (RadioButton) type_view.findViewById(R.id.band_4);
        RadioButton bands5 = (RadioButton) type_view.findViewById(R.id.band_5);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button navigate = (Button) type_view.findViewById(R.id.bands_button);
                navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(bands4.isChecked()) {
                            alertDialog.dismiss();
                            Toast.makeText(getBaseContext(), "Page under construction", Toast.LENGTH_LONG).show();
                        }
                        else {
                            alertDialog.dismiss();
                            Intent intent = new Intent(LandingActivity.this, ResistorActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }
}
