package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by warmach on 30/7/16.
 */
public class GateInformationActivity extends Activity {

    TextView gateNameTextView;
    ImageView gateTruthImageView, gateImageView;
    Intent intent;
    Button showPinOutButton;
    int[] gate_images = {R.drawable.and_gate, R.drawable.or_gate, R.drawable.xor_gate, R.drawable.nand_gate, R.drawable.nor_gate, R.drawable.xnor_gate, R.drawable.not_gate};
    int[] truth_images = {R.drawable.and_truth, R.drawable.or_truth, R.drawable.xor_truth, R.drawable.nand_truth, R.drawable.nor_truth, R.drawable.xnor_truth, R.drawable.not_truth};
    int[] ttl_pinouts = {R.drawable.ttl_and, R.drawable.ttl_or, R.drawable.ttl_xor, R.drawable.ttl_nand, R.drawable.ttl_nor, R.drawable.ttl_xnor, R.drawable.ttl_not};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_information);
        intent = getIntent();
        bindViews();
        setGateName();
        setListeners();
    }

    public void bindViews() {
        gateNameTextView = (TextView) findViewById(R.id.gate_name_textview);
        gateTruthImageView = (ImageView) findViewById(R.id.gate_truth_table_imageview);
        gateImageView = (ImageView) findViewById(R.id.gate_imageview);
        showPinOutButton = (Button) findViewById(R.id.show_pin_diagram_button);
    }

    public void setListeners() {
        showPinOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPinTypePopup();
            }
        });
    }

    public void setGateName() {
        String gateType = intent.getStringExtra("gate_type");
        switch (gateType){
            case "and":
                gateNameTextView.setText("AND Gate");
                gateImageView.setBackgroundResource(gate_images[0]);
                gateTruthImageView.setBackgroundResource(truth_images[0]);
                break;
            case "or":
                gateNameTextView.setText("OR Gate");
                gateImageView.setBackgroundResource(gate_images[1]);
                gateTruthImageView.setBackgroundResource(truth_images[1]);
                break;
            case "xor":
                gateNameTextView.setText("XOR Gate");
                gateImageView.setBackgroundResource(gate_images[2]);
                gateTruthImageView.setBackgroundResource(truth_images[2]);
                break;
            case "nand":
                gateNameTextView.setText("NAND Gate");
                gateImageView.setBackgroundResource(gate_images[3]);
                gateTruthImageView.setBackgroundResource(truth_images[3]);
                break;
            case "nor":
                gateNameTextView.setText("NOR Gate");
                gateImageView.setBackgroundResource(gate_images[4]);
                gateTruthImageView.setBackgroundResource(truth_images[4]);
                break;
            case "xnor":
                gateNameTextView.setText("XNOR Gate");
                gateImageView.setBackgroundResource(gate_images[5]);
                gateTruthImageView.setBackgroundResource(truth_images[5]);
                break;
            case "not":
                gateNameTextView.setText("NOT Gate");
                gateImageView.setBackgroundResource(gate_images[6]);
                gateTruthImageView.setBackgroundResource(truth_images[6]);
                break;
        }
    }

    public void showPinTypePopup() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.pin_out_type_popup, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final RadioButton ttl = (RadioButton) view.findViewById(R.id.ttl_radioButton);
                final RadioButton cmos = (RadioButton) view.findViewById(R.id.cmos_radioButton);
                Button confirm = (Button) view.findViewById(R.id.chip_type_button);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ttl.isChecked())
                        {
                            alertDialog.dismiss();
                            showTtlPopup();
                        }
                        else if(cmos.isChecked())
                        {
                            alertDialog.dismiss();
                            showCmosPopup();
                        }
                        else
                            Toast.makeText(GateInformationActivity.this, "Please select an option", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void showTtlPopup(){
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.pinout_popup, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                TextView chipname = (TextView) view.findViewById(R.id.chip_name_textview);
                ImageView chipPinImage = (ImageView) view.findViewById(R.id.pin_imageview);
                Button close = (Button) view.findViewById(R.id.close_chip_button);
                setTtlDetails(chipname, chipPinImage);
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

    public void setTtlDetails(TextView chipName, ImageView chipPinImage) {
        String chipType = intent.getStringExtra("gate_type");
        switch (chipType){
            case "and":
                chipName.setText("74LS08");
                chipPinImage.setBackgroundResource(ttl_pinouts[0]);
                break;
            case "or":
                chipName.setText("74LS32");
                chipPinImage.setBackgroundResource(ttl_pinouts[1]);
                break;
            case "xor":
                chipName.setText("74LS86");
                chipPinImage.setBackgroundResource(ttl_pinouts[2]);
                break;
            case "nand":
                chipName.setText("74LS00");
                chipPinImage.setBackgroundResource(ttl_pinouts[3]);
                break;
            case "nor":
                chipName.setText("74LS02");
                chipPinImage.setBackgroundResource(ttl_pinouts[4]);
                break;
            case "xnor":
                chipName.setText("74LS266");
                chipPinImage.setBackgroundResource(ttl_pinouts[5]);
                break;
            case "not":
                chipName.setText("74LS04");
                chipPinImage.setBackgroundResource(ttl_pinouts[6]);
                break;
        }
    }

    public void showCmosPopup() {

    }


}
