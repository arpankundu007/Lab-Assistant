package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

/**
 * Created by warmach on 18/7/16.
 */
public class ResistorActivity extends Activity {
    ImageButton firstColor, secondColor, thirdColor, fourthColor, toleranceColor;
    ImageView  convertedFirstColor, convertedSecondColor, convertedThirdColor, convertedFourthColor, convertedToleranceColor;
    Button convertResistance, getResistance, parseResistance;
    int firstValue = 0, secondValue = 0, thirdValue = 0, fourthValue = 0, toleranceValue = 0;
    TextView resistanceTextView, toleranceTextView;
    EditText enteredResistanceValue, enteredResistancePower;
    int[] colorArray = {R.drawable.black, R.drawable.brown, R.drawable.red, R.drawable.orange, R.drawable.yellow, R.drawable.green, R.drawable.blue, R.drawable.violet,
            R.drawable.gray, R.drawable.white, R.drawable.gold};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resistor);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        firstColor = (ImageButton) findViewById(R.id.first);
        secondColor = (ImageButton) findViewById(R.id.second);
        thirdColor = (ImageButton) findViewById(R.id.third);
        fourthColor = (ImageButton) findViewById(R.id.fourth);
        toleranceColor = (ImageButton) findViewById(R.id.tolerance);
        convertResistance = (Button) findViewById(R.id.resistance_conversion);
        getResistance = (Button) findViewById(R.id.show_resistance);
        resistanceTextView = (TextView) findViewById(R.id.resistance_value);
        toleranceTextView = (TextView) findViewById(R.id.tolerance_value);
        enteredResistanceValue = (EditText) findViewById(R.id.resistance_entered);
        enteredResistancePower = (EditText) findViewById(R.id.resistance_power);
        parseResistance = (Button) findViewById(R.id.parse_resistance);
        convertedFirstColor = (ImageView) findViewById(R.id.converted_first);
        convertedSecondColor = (ImageView) findViewById(R.id.converted_second);
        convertedThirdColor = (ImageView) findViewById(R.id.converted_third);
        convertedFourthColor = (ImageView) findViewById(R.id.converted_fourth);
        convertedToleranceColor = (ImageView) findViewById(R.id.converted_tolerance);
    }

    public void setListeners() {
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
        parseResistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseResistance();
            }
        });
    }

    public void showDialog(final ImageButton imageButton) {
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
                        if (imageButton == firstColor)
                            firstValue = color;
                        else if (imageButton == secondColor)
                            secondValue = color;
                        else if (imageButton == thirdColor)
                            thirdValue = color;
                        else if (imageButton == fourthColor)
                            fourthValue = color;
                        else if (imageButton == toleranceColor)
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

    public void setColor(int color, ImageButton imageButton) {
        switch (color) {
            case 0:
                if (imageButton == firstColor && color == 0)
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

    public void showConversionDialog() {
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
        String resistance = "" + firstValue + "" + secondValue + "" + thirdValue + " x 10^" + fourthValue + " Ohms";
        String tolerance = " " + toleranceValue + " % Tol";
        resistanceTextView.setText(resistance);
        toleranceTextView.setText(tolerance);
    }

    public void parseResistance() {
        String resistance = enteredResistanceValue.getText().toString();
        String power = enteredResistancePower.getText().toString();
        float resistanceFloat = Float.parseFloat(resistance);
        float powerFloat = Float.parseFloat(power);
        String parsedResistance;
        int zerosLeft;
        if(powerFloat >= 3) {
            parsedResistance = new DecimalFormat("##").format((float) (resistanceFloat * Math.pow(10, 3)));
            zerosLeft = (int) (powerFloat - 3);
        }
        else {
            parsedResistance = new DecimalFormat("##").format((float) (resistanceFloat * Math.pow(10, powerFloat)));
            zerosLeft = 0;
        }
        while(zerosLeft > 1){
            parsedResistance = parsedResistance + "0";
            zerosLeft -- ;
        }
        parsedResistance = parsedResistance.replace(".","");
        setResistanceColors(parsedResistance);
    }

    public void setResistanceColors(String resistanceValue) {
        int col_1 = Integer.parseInt(String.valueOf(resistanceValue.charAt(0)));
        int col_2 = Integer.parseInt(String.valueOf(resistanceValue.charAt(1)));
        int col_3 = Integer.parseInt(String.valueOf(resistanceValue.charAt(2)));
        int col_4 = resistanceValue.length() - 3;
        if(col_4 > 11)
            Toast.makeText(getBaseContext(), "Out of bounds exception", Toast.LENGTH_LONG).show();
        convertedFirstColor.setImageResource(colorArray[col_1]);
        convertedSecondColor.setImageResource(colorArray[col_2]);
        convertedThirdColor.setImageResource(colorArray[col_3]);
        convertedFourthColor.setImageResource(colorArray[col_4]);
    }

}
