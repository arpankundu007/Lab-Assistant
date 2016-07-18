package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by warmach on 18/7/16.
 */
public class ResistorActivity extends Activity {
    ImageButton firstColor, secondColor, thirdColor, fourthColor, toleranceColor;
    Button convertResistance, getResistance;
    int firstValue = 0, secondValue = 0, thirdValue = 0, fourthValue = 0, toleranceValue = 0;
    TextView resistanceTextView, toleranceTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resistor);
        bindViews();
        setListeners();
    }
    public void bindViews(){
        firstColor = (ImageButton) findViewById(R.id.first);
        secondColor = (ImageButton) findViewById(R.id.second);
        thirdColor = (ImageButton) findViewById(R.id.third);
        fourthColor = (ImageButton) findViewById(R.id.fourth);
        toleranceColor = (ImageButton) findViewById(R.id.tolerance);
        convertResistance = (Button) findViewById(R.id.resistance_conversion);
        getResistance = (Button) findViewById(R.id.show_resistance);
        resistanceTextView = (TextView) findViewById(R.id.resistance_value);
        toleranceTextView = (TextView) findViewById(R.id.tolerance_value);
    }

    public void setListeners(){
        firstColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(firstColor);
            }
        });
        secondColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(secondColor);
            }
        });
        thirdColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(thirdColor);
            }
        });
        fourthColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(fourthColor);
            }
        });
        toleranceColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(toleranceColor);
            }
        });
        convertResistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConversionDialog();
            }
        });
        getResistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateResistance();
            }
        });
    }

    public void showDialog(final ImageButton imageButton){
        LayoutInflater inflater = getLayoutInflater();
        final View colorPopup = inflater.inflate(R.layout.color_picker_alert, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).
                setView(colorPopup).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = (Button) colorPopup.findViewById(R.id.confirmColor);
                Button negative = (Button) colorPopup.findViewById(R.id.cancelColor);
                final Spinner colorSpinner = (Spinner) colorPopup.findViewById(R.id.color_spinner);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = colorSpinner.getSelectedItemPosition();
                        setColor(color, imageButton);
                        if(imageButton == firstColor)
                            firstValue = color;
                        else if(imageButton == secondColor)
                            secondValue = color;
                        else if(imageButton == thirdColor)
                            thirdValue = color;
                        else if(imageButton == fourthColor)
                            fourthValue = color;
                        else if(imageButton == toleranceColor)
                            toleranceValue = color;
                        alertDialog.dismiss();
                    }
                });
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void setColor(int color, ImageButton imageButton){
        switch (color){
            case 0:
                if(imageButton == firstColor && color == 0)
                    Toast.makeText(getBaseContext(), "First band cannot be black", Toast.LENGTH_LONG).show();
                else
                    imageButton.setBackgroundColor(Color.BLACK);
                break;
            case 1:
                imageButton.setBackgroundColor(Color.parseColor("#a52a2a"));
                break;
            case 2:
                imageButton.setBackgroundColor(Color.RED);
                break;
            case 3:
                imageButton.setBackgroundColor(Color.parseColor("#ffa500"));
                break;
            case 4:
                imageButton.setBackgroundColor(Color.YELLOW);
                break;
            case 5:
                imageButton.setBackgroundColor(Color.GREEN);
                break;
            case 6:
                imageButton.setBackgroundColor(Color.BLUE);
                break;
            case 7:
                imageButton.setBackgroundColor(Color.parseColor("#ca1cca"));
                break;
            case 8:
                imageButton.setBackgroundColor(Color.GRAY);
                break;
            case 9:
                imageButton.setBackgroundColor(Color.WHITE);
                break;
            case 10:
                imageButton.setBackgroundColor(Color.parseColor("#ffd700"));
                break;


        }
    }

    public void showConversionDialog(){
        LayoutInflater inflater = getLayoutInflater();
        final View conversionDialogView = inflater.inflate(R.layout.resistance_conversion_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).
                setView(conversionDialogView).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button close = (Button) conversionDialogView.findViewById(R.id.close_conversion);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void calculateResistance() {
        String resistance = "" + firstValue + "" + secondValue + "" + thirdValue + " x 10^" + fourthValue + " Ohms" ;
        String tolerance = " " + toleranceValue + " % Tol";
        resistanceTextView.setText(resistance);
        toleranceTextView.setText(tolerance);
    }
}
