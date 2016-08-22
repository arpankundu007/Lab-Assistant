package com.labmate.warmach.labmate;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by warmach on 14/8/16.
 */
public class Decoder38Activity extends Activity {
    Spinner decoderTypeSpinner, x0Spinner, x2Spinner, x1Spinner;
    ImageView decoderImageView;
    Button decodeButton, downloadDecoderDatasheetButton, downloadEncoderDatasheetButton, decoderCircuitButton, encoderChartButton, encoderCircuitButton;
    ScrollView scrollView;
    TextView y0, y1, y2, y3, y4, y5, y6, y7;
    RelativeLayout decoderRelativeLayout, encoderRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder_38);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        decoderTypeSpinner = (Spinner) findViewById(R.id.decoder_type_spinner);
        decoderImageView = (ImageView) findViewById(R.id.decoder_imageview);
        x2Spinner = (Spinner) findViewById(R.id.decode_x2_spinner);
        x1Spinner = (Spinner) findViewById(R.id.decode_x1_spinner);
        x0Spinner = (Spinner) findViewById(R.id.decode_x0_spinner);
        decodeButton = (Button) findViewById(R.id.decode_button);
        scrollView = (ScrollView) findViewById(R.id.decoder_scrollview);
        y0 = (TextView) findViewById(R.id.decode_y0_textview);
        y1 = (TextView) findViewById(R.id.decode_y1_textview);
        y2 = (TextView) findViewById(R.id.decode_y2_textview);
        y3 = (TextView) findViewById(R.id.decode_y3_textview);
        y4 = (TextView) findViewById(R.id.decode_y4_textview);
        y5 = (TextView) findViewById(R.id.decode_y5_textview);
        y6 = (TextView) findViewById(R.id.decode_y6_textview);
        y7 = (TextView) findViewById(R.id.decode_y7_textview);
        decoderRelativeLayout = (RelativeLayout) findViewById(R.id.decoder_relative);
        encoderRelativeLayout = (RelativeLayout) findViewById(R.id.encoder_relative);
        downloadDecoderDatasheetButton = (Button) findViewById(R.id.decoder_datasheet_button);
        downloadEncoderDatasheetButton = (Button) findViewById(R.id.encoder_datasheet_button);
        decoderCircuitButton = (Button) findViewById(R.id.decoder_circuit_button);
        encoderChartButton = (Button) findViewById(R.id.encoder_chart_button);
        encoderCircuitButton = (Button) findViewById(R.id.encoder_circuit_button);
    }

    public void setListeners() {
        decoderTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    decoderImageView.setBackgroundResource(R.drawable.decoder_block);
                    showDecoder();
                }
                else {
                    decoderImageView.setBackgroundResource(R.drawable.encoder_block);
                    showEncoder();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        decodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getScrollY() + 500).setDuration(1000).start();
                calculateDecodedBits();
            }
        });
        x2Spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getScrollY() + 500).setDuration(1000).start();
                return false;
            }
        });

        downloadDecoderDatasheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.downloadDatasheet(Decoder38Activity.this, "https://www.fairchildsemi.com/datasheets/MM/MM74HC138.pdf");
            }
        });

        downloadEncoderDatasheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.downloadDatasheet(Decoder38Activity.this, "http://www.ti.com/lit/ds/symlink/sn74ls348.pdf");
            }
        });

        decoderCircuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDecoderCircuitPopup();
            }
        });
        encoderChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEncoderChartPopup();
            }
        });

        encoderCircuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEncoderCircuitPopup();
            }
        });
    }

    public void calculateDecodedBits() {
        int x0, x1, x2, value;
        x0 = x0Spinner.getSelectedItemPosition();
        x1 = x1Spinner.getSelectedItemPosition();
        x2 = x2Spinner.getSelectedItemPosition();
        value = (int) ((x2 * Math.pow(2, 2)) + (x1 * Math.pow(2, 1)) + (x0));
        Log.v("Value", "" + value);
        setOutput(value);
    }

    public void setOutput(int value) {
        setZeros();
        switch (value){
            case 0:
                y0.setText("1");
                break;
            case 1:
                y1.setText("1");
                break;
            case 2:
                y2.setText("1");
                break;
            case 3:
                y3.setText("1");
                break;
            case 4:
                y4.setText("1");
                break;
            case 5:
                y5.setText("1");
                break;
            case 6:
                y6.setText("1");
                break;
            case 7:
                y7.setText("1");
                break;
        }
    }

    public void setZeros() {
        y0.setText("0");
        y1.setText("0");
        y2.setText("0");
        y3.setText("0");
        y4.setText("0");
        y5.setText("0");
        y6.setText("0");
        y7.setText("0");
    }

    public void showDecoder() {
        decoderRelativeLayout.setVisibility(View.VISIBLE);
        encoderRelativeLayout.setVisibility(View.GONE);
    }

    public void showEncoder() {
        decoderRelativeLayout.setVisibility(View.GONE);
        encoderRelativeLayout.setVisibility(View.VISIBLE);
    }

    public void showDecoderCircuitPopup() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.decoder_circuit_popup, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(false).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button close = (Button) view.findViewById(R.id.close_decoder_circuit_button);
                ImageView circuit = (ImageView) view.findViewById(R.id.decoder_circuit_imageview);
                TextView chipname = (TextView) view.findViewById(R.id.decoder_chip_number);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                circuit.setBackgroundResource(R.drawable.decoder_circuit);
                chipname.setText("74HC138");
            }
        });
        alertDialog.show();
    }

    public void showEncoderChartPopup() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.decoder_circuit_popup, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(false).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button close = (Button) view.findViewById(R.id.close_decoder_circuit_button);
                ImageView circuit = (ImageView) view.findViewById(R.id.decoder_circuit_imageview);
                TextView chipname = (TextView) view.findViewById(R.id.decoder_chip_number);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                circuit.setBackgroundResource(R.drawable.encoder_chart);
                chipname.setText("8:3 Priority Encoder");
            }
        });
        alertDialog.show();
    }

    public void showEncoderCircuitPopup() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.decoder_circuit_popup, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(false).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button close = (Button) view.findViewById(R.id.close_decoder_circuit_button);
                ImageView circuit = (ImageView) view.findViewById(R.id.decoder_circuit_imageview);
                TextView chipname = (TextView) view.findViewById(R.id.decoder_chip_number);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                circuit.setBackgroundResource(R.drawable.encoder_circuit);
                chipname.setText("74LS348");
            }
        });
        alertDialog.show();
    }

}
