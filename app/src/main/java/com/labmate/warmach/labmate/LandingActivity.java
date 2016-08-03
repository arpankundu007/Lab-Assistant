package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class LandingActivity extends Activity {

    RelativeLayout resistorRelativeLayout, capacitorRelativeLayout, inductorRelativeLayout, digitalRelativeLayout, analogRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setListeners();
    }

    public void bindViews(){
        resistorRelativeLayout = (RelativeLayout) findViewById(R.id.resistor_relativelayout);
        capacitorRelativeLayout = (RelativeLayout) findViewById(R.id.capacitor_relativelayout);
        inductorRelativeLayout = (RelativeLayout) findViewById(R.id.inductor_relativeLayout);
        digitalRelativeLayout = (RelativeLayout) findViewById(R.id.digital_relativeLayout);
        analogRelativeLayout = (RelativeLayout) findViewById(R.id.analog_relativeLayout);
    }

    public void setListeners() {
        resistorRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, ResistorActivity.class);
                startActivity(intent);

            }
        });
        capacitorRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, CapacitorActivity.class);
                startActivity(intent);
            }
        });
        inductorRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, InductorActivity.class);
                startActivity(intent);
            }
        });
        digitalRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, DigitalComponentsActivity.class);
                startActivity(intent);
            }
        });
        analogRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LandingActivity.this, "Analog components under construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showBandPopup() {
        LayoutInflater inflater = getLayoutInflater();
        final View type_view = inflater.inflate(R.layout.resistor_bands, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).
                setView(type_view).setCancelable(true).create();
        final RadioButton bands4 = (RadioButton) type_view.findViewById(R.id.band_4);
        final RadioButton bands5 = (RadioButton) type_view.findViewById(R.id.band_5);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button navigate = (Button) type_view.findViewById(R.id.bands_button);
                navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(bands4.isChecked()) {
                            alertDialog.dismiss();
                            Toast.makeText(LandingActivity.this, "Page under construction", Toast.LENGTH_SHORT).show();
                        }
                        else if(bands5.isChecked()){
                            alertDialog.dismiss();
                            Intent intent = new Intent(LandingActivity.this, ResistorActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(getBaseContext(), "Please select an option", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        alertDialog.show();
    }
}
