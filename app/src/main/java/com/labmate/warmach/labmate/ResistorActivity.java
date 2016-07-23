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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by warmach on 18/7/16.
 */
public class ResistorActivity extends Activity {
    ImageButton firstColor, secondColor, thirdColor, fourthColor, toleranceColor;
    ImageView convertedFirstColor, convertedSecondColor, convertedThirdColor, convertedFourthColor, convertedToleranceColor;
    Button convertResistance, getResistance, parseResistance;
    int firstValue = 0, secondValue = 0, thirdValue = 0, fourthValue = 0;
    String toleranceValue;
    TextView resistanceTextView, toleranceTextView;
    EditText enteredResistanceValue, enteredResistancePower;
    int[] colorArray = {R.drawable.black, R.drawable.brown, R.drawable.red, R.drawable.orange, R.drawable.yellow, R.drawable.green, R.drawable.blue, R.drawable.violet,
            R.drawable.gray, R.drawable.white, R.drawable.gold};
    int[] tolColorArray = {R.drawable.brown, R.drawable.red, R.drawable.green, R.drawable.blue, R.drawable.violet, R.drawable.gray, R.drawable.gold, R.drawable.silver};
    Spinner toleranceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resistor);
        bindViews();
        setDefaults();
        setListeners();
    }

    public void setDefaults() {
        firstColor.setBackgroundColor(Color.parseColor("#a52a2a"));
        firstValue = 1;
        secondColor.setBackgroundColor(Color.BLACK);
        secondValue = 0;
        thirdColor.setBackgroundColor(Color.BLACK);
        thirdValue = 0;
        fourthColor.setBackgroundColor(Color.BLACK);
        fourthValue = 0;
        toleranceColor.setBackgroundColor(Color.BLUE);
        toleranceValue = "0.25%";
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
        convertedToleranceColor = (ImageView) findViewById(R.id.tolerance_color);
        toleranceSpinner = (Spinner) findViewById(R.id.tol_spinner);
    }

    public void setListeners() {
        firstColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResistanceDialog(firstColor);
            }
        });
        secondColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResistanceDialog(secondColor);
            }
        });
        thirdColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResistanceDialog(thirdColor);
            }
        });
        fourthColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResistanceDialog(fourthColor);
            }
        });
        toleranceColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToleranceDialog();
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
                setResistance();
            }
        });
        parseResistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseResistance();
                setToleranceColor();
            }
        });
    }

    public void setToleranceColor() {
        int value = toleranceSpinner.getSelectedItemPosition();
        Log.v("Tolerance position", ""+ value);
        switch(value){
            case 0:
                convertedToleranceColor.setImageResource(tolColorArray[0]);
                break;
            case 1:
                convertedToleranceColor.setImageResource(tolColorArray[1]);
                break;
            case 2:
                convertedToleranceColor.setImageResource(tolColorArray[2]);
                break;
            case 3:
                convertedToleranceColor.setImageResource(tolColorArray[3]);
                break;
            case 4:
                convertedToleranceColor.setImageResource(tolColorArray[4]);
                break;
            case 5:
                convertedToleranceColor.setImageResource(tolColorArray[5]);
                break;
            case 6:
                convertedToleranceColor.setImageResource(tolColorArray[6]);
                break;
            case 7:
                convertedToleranceColor.setImageResource(tolColorArray[7]);
                break;
        }
    }

    public void showResistanceDialog(final ImageButton imageButton) {
        LayoutInflater inflater = getLayoutInflater();
        final View colorPopup = inflater.inflate(R.layout.color_picker_alert, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).
                setView(colorPopup).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = (Button) colorPopup.findViewById(R.id.confirmColor);
                Button negative = (Button) colorPopup.findViewById(R.id.cancelColor);
                final Spinner colorSpinner = (Spinner) colorPopup.findViewById(R.id.tolerance_color_spinner);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = colorSpinner.getSelectedItemPosition();
                        if (imageButton == firstColor)
                            firstValue = color;
                        else if (imageButton == secondColor)
                            secondValue = color;
                        else if (imageButton == thirdColor)
                            thirdValue = color;
                        else if (imageButton == fourthColor)
                            fourthValue = color;
                        setColor(color, imageButton);
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

    public void showToleranceDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        final View tolerancePopup = layoutInflater.inflate(R.layout.tolerance_alert, null);
        final AlertDialog toleranceDialog = new AlertDialog.Builder(this).setView(tolerancePopup).setCancelable(true).create();
        toleranceDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = (Button) tolerancePopup.findViewById(R.id.confirmColor);
                Button negative = (Button) tolerancePopup.findViewById(R.id.cancelColor);
                final Spinner colorSpinner = (Spinner) tolerancePopup.findViewById(R.id.tolerance_color_spinner);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = colorSpinner.getSelectedItemPosition();
                        setTolerance(color);
                        toleranceDialog.dismiss();
                    }
                });
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toleranceDialog.dismiss();
                    }
                });
            }
        });
        toleranceDialog.show();
    }

    public void setTolerance(int color) {
        switch (color){
            case 0:
                toleranceColor.setBackgroundColor(Color.parseColor("#a52a2a"));
                toleranceValue = "1%";
                break;
            case 1:
                toleranceColor.setBackgroundColor(Color.RED);
                toleranceValue = "2%";
                break;
            case 2:
                toleranceColor.setBackgroundColor(Color.parseColor("#006600"));
                toleranceValue = "0.5%";
                break;
            case 3:
                toleranceColor.setBackgroundColor(Color.BLUE);
                toleranceValue = "0.25%";
                break;
            case 4:
                toleranceColor.setBackgroundColor(Color.parseColor("#ca1cca"));
                toleranceValue = "0.1%";
                break;
            case 5:
                toleranceColor.setBackgroundColor(Color.GRAY);
                toleranceValue = "0.05%";
                break;
            case 6:
                toleranceColor.setBackgroundColor(Color.parseColor("#ffd700"));
                toleranceValue = "5%";
                break;
            case 7:
                toleranceColor.setBackgroundColor(Color.parseColor("#c0c0c0"));
                toleranceValue = "10%";
                break;
        }
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
                imageButton.setBackgroundColor(Color.parseColor("#006600"));
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

    public void setResistance() {
        String resistance = "" + firstValue + "" + secondValue + "" + thirdValue + " x 10^" + fourthValue + " Ohms";
        String tolerance = toleranceValue + " Tol";
        resistanceTextView.setText(resistance);
        toleranceTextView.setText(tolerance);
    }

    public void parseResistance() {
        if (setValidations() == true) {
            String resistance = enteredResistanceValue.getText().toString();
            String power = enteredResistancePower.getText().toString();
            float resistanceFloat = Float.parseFloat(resistance);
            float powerFloat = Float.parseFloat(power);
            String parsedResistance;
            int zerosLeft;
            if (powerFloat >= 3) {
                parsedResistance = new DecimalFormat("##").format((float) (resistanceFloat * Math.pow(10, 3)));
                zerosLeft = (int) (powerFloat - 3);
            } else {
                parsedResistance = new DecimalFormat("##").format((float) (resistanceFloat * Math.pow(10, powerFloat)));
                zerosLeft = 0;
            }
            while (zerosLeft > 0) {
                parsedResistance = parsedResistance + "0";
                zerosLeft--;
            }
            parsedResistance = parsedResistance.replace(".", "");
            Log.v("Parsed res: ", parsedResistance);
            setResistanceColors(parsedResistance);
        }
    }

    public void setResistanceColors(String resistanceValue) {
        int col_1 = Integer.parseInt(String.valueOf(resistanceValue.charAt(0)));
        int col_2 = Integer.parseInt(String.valueOf(resistanceValue.charAt(1)));
        int col_3 = Integer.parseInt(String.valueOf(resistanceValue.charAt(2)));
        int col_4 = resistanceValue.length() - 3;
        if (col_4 > 11)
            Toast.makeText(getBaseContext(), "Out of bounds exception", Toast.LENGTH_LONG).show();
        convertedFirstColor.setImageResource(colorArray[col_1]);
        convertedSecondColor.setImageResource(colorArray[col_2]);
        convertedThirdColor.setImageResource(colorArray[col_3]);
        convertedFourthColor.setImageResource(colorArray[col_4]);
    }

    public boolean setValidations() {
        if (enteredResistanceValue.getText().toString().length() < 3 || enteredResistancePower.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Invalid data entered", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
