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
 * Created by warmach on 23/7/16.
 */
public class InductorActivity extends Activity {
    Button conversionChartButton, showInductanceButton, parseInductanceButton;
    ImageButton firstColor, secondColor, thirdColor, toleranceColor;
    EditText enteredInductanceValue, enteredInductancePower;
    int firstValue = 0, secondValue = 0, thirdValue = 0;
    String toleranceValue;
    ImageView convertedFirstColor, convertedSecondColor, convertedThirdColor, convertedToleranceColor;
    TextView inductanceTextView, toleranceTextView;
    Spinner toleranceSpinner;
    int[] colorArray = {R.drawable.black, R.drawable.brown, R.drawable.red, R.drawable.orange, R.drawable.yellow, R.drawable.green, R.drawable.blue, R.drawable.violet,
            R.drawable.gray, R.drawable.white, R.drawable.gold};
    int[] tolColorArray = {R.drawable.black, R.drawable.brown, R.drawable.red, R.drawable.orange, R.drawable.yellow, R.drawable.gold, R.drawable.silver};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inductor);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        firstColor = (ImageButton) findViewById(R.id.first);
        secondColor = (ImageButton) findViewById(R.id.second);
        thirdColor = (ImageButton) findViewById(R.id.third);
        toleranceColor = (ImageButton) findViewById(R.id.tolerance);
        conversionChartButton = (Button) findViewById(R.id.inductance_conversion);
        showInductanceButton = (Button) findViewById(R.id.show_component_value);
        inductanceTextView = (TextView) findViewById(R.id.inductance_value);
        toleranceTextView = (TextView) findViewById(R.id.tolerance_value);
        enteredInductanceValue = (EditText) findViewById(R.id.base_entered);
        enteredInductancePower = (EditText) findViewById(R.id.power_entered);
        convertedFirstColor = (ImageView) findViewById(R.id.converted_first);
        convertedSecondColor = (ImageView) findViewById(R.id.converted_second);
        convertedThirdColor = (ImageView) findViewById(R.id.converted_third);
        convertedToleranceColor = (ImageView) findViewById(R.id.tolerance_color);
        parseInductanceButton = (Button) findViewById(R.id.parse_component);
        toleranceSpinner = (Spinner) findViewById(R.id.tol_spinner);
    }

    public void setListeners() {
        conversionChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConversionDialog();
            }
        });
        firstColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetValues();
                showInductanceDialog(firstColor);
            }
        });
        secondColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetValues();
                showInductanceDialog(secondColor);
            }
        });
        thirdColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetValues();
                inductanceMultiplier(thirdColor);
            }
        });
        toleranceColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetValues();
                showToleranceDialog();
            }
        });
        showInductanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInductance();
            }
        });
        parseInductanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseInductance();
                setToleranceColor();
            }
        });
    }

    public void showInductanceDialog(final ImageButton imageButton) {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.color_picker_alert, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = (Button) view.findViewById(R.id.confirmColor);
                Button negative = (Button) view.findViewById(R.id.cancelColor);
                final Spinner colorSpinner = (Spinner) view.findViewById(R.id.multiplier_color_spinner);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = colorSpinner.getSelectedItemPosition();
                        if (imageButton == firstColor)
                            firstValue = color;
                        else if (imageButton == secondColor)
                            secondValue = color;
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

    public void inductanceMultiplier(final ImageButton imageButton) {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.inductance_multiplier, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = (Button) view.findViewById(R.id.confirmColor);
                Button negative = (Button) view.findViewById(R.id.cancelColor);
                final Spinner colorSpinner = (Spinner) view.findViewById(R.id.multiplier_color_spinner);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int color = colorSpinner.getSelectedItemPosition();
                        if (imageButton == thirdColor)
                            thirdValue = color;
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
        final View tolerancePopup = layoutInflater.inflate(R.layout.inductor_tolerance_dialog, null);
        final AlertDialog toleranceDialog = new AlertDialog.Builder(this).setView(tolerancePopup).setCancelable(true).create();
        toleranceDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = (Button) tolerancePopup.findViewById(R.id.confirmColor);
                Button negative = (Button) tolerancePopup.findViewById(R.id.cancelColor);
                final Spinner colorSpinner = (Spinner) tolerancePopup.findViewById(R.id.multiplier_color_spinner);
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
        switch (color) {
            case 0:
                toleranceColor.setBackgroundColor(Color.BLACK);
                toleranceValue = "20%";
                break;
            case 1:
                toleranceColor.setBackgroundColor(Color.parseColor("#a52a2a"));
                toleranceValue = "1%";
                break;
            case 2:
                toleranceColor.setBackgroundColor(Color.RED);
                toleranceValue = "2%";
                break;
            case 3:
                toleranceColor.setBackgroundColor(Color.parseColor("#ffa500"));
                toleranceValue = "3%";
                break;
            case 4:
                toleranceColor.setBackgroundColor(Color.YELLOW);
                toleranceValue = "4%";
                break;
            case 5:
                toleranceColor.setBackgroundColor(Color.parseColor("#ffd700"));
                toleranceValue = "5%";
                break;
            case 6:
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

    public void setInductance() {
        String inductance = "" + firstValue + "" + secondValue + " x 10 ^ " + thirdValue + " μH";
        String tolerance = toleranceValue + " Tol";
        inductanceTextView.setText(inductance);
        toleranceTextView.setText(tolerance);
    }

    public void parseInductance() {
        if(setValidations()) {
            String inductance = enteredInductanceValue.getText().toString();
            String power = enteredInductancePower.getText().toString();
            float inductanceFloat = Float.parseFloat(inductance);
            float powerFloat = Float.parseFloat(power);
            String parsedInductance;
            int zerosLeft;
            if (powerFloat >= 3) {
                parsedInductance = new DecimalFormat("##").format((float) (inductanceFloat * Math.pow(10, 3)));
                zerosLeft = (int) (powerFloat - 3);
            } else {
                parsedInductance = new DecimalFormat("##").format((float) (inductanceFloat * Math.pow(10, powerFloat)));
                zerosLeft = 0;
            }
            while (zerosLeft > 0) {
                parsedInductance = parsedInductance + "0";
                zerosLeft--;
            }
            parsedInductance = parsedInductance.replace(".", "");
            if(!parsedInductance.equals("") && Integer.parseInt(inductance.replace(".","")) < 99)
                setInductanceColors(parsedInductance);
            else
                Toast.makeText(getBaseContext(), "Please enter base of inductance less than 100", Toast.LENGTH_LONG).show();
        }
    }

    public void setInductanceColors(String inductance) {
        int col_1 = Integer.parseInt(String.valueOf(inductance.charAt(0)));
        int col_2 = Integer.parseInt(String.valueOf(inductance.charAt(1)));
        int col_3 = inductance.length() - 2;
        if (col_3 > 11)
            Toast.makeText(getBaseContext(), "Out of bounds exception", Toast.LENGTH_LONG).show();
        convertedFirstColor.setImageResource(colorArray[col_1]);
        convertedSecondColor.setImageResource(colorArray[col_2]);
        convertedThirdColor.setImageResource(colorArray[col_3]);
    }

    public void setToleranceColor() {
        if(setValidations()) {
            int value = toleranceSpinner.getSelectedItemPosition();
            switch (value) {
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
    }

    public void showConversionDialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View conversionDialogView = inflater.inflate(R.layout.inductor_conversion, null);
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

    public void resetValues() {
        inductanceTextView.setText("");
        toleranceTextView.setText("");
    }

    public boolean setValidations() {
        if (enteredInductanceValue.getText().toString().length() < 2 || enteredInductancePower.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "Invalid data entered", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
