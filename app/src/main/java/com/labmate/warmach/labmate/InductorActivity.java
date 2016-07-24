package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by warmach on 23/7/16.
 */
public class InductorActivity extends Activity {
    Button conversionChartButton, showInductanceButton;
    ImageButton firstColor, secondColor, thirdColor, toleranceColor;
    int firstValue = 0, secondValue = 0, thirdValue = 0;
    String toleranceValue;
    TextView inductanceTextView, toleranceTextView;
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
        showInductanceButton = (Button) findViewById(R.id.show_inductance);
        inductanceTextView = (TextView) findViewById(R.id.inductance_value);
        toleranceTextView = (TextView) findViewById(R.id.tolerance_value);
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
                showInductanceDialog(firstColor);
            }
        });
        secondColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInductanceDialog(secondColor);
            }
        });
        thirdColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inductanceMultiplier(thirdColor);
            }
        });
        toleranceColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToleranceDialog();
            }
        });
        showInductanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInductance();
            }
        });
    }

    public void showInductanceDialog(final ImageButton imageButton){
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
                        else if (imageButton == thirdColor)
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
        switch (color){
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

}
