package com.labmate.warmach.labmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by warmach on 30/7/16.
 */
public class GateInformationActivity extends Activity {

    TextView gateNameTextView;
    ImageView gateTruthImageView, gateImageView;
    Intent intent;
    int[] gate_images = {R.drawable.and_gate, R.drawable.or_gate, R.drawable.xor_gate, R.drawable.nand_gate, R.drawable.nor_gate, R.drawable.xnor_gate, R.drawable.not_gate};
    int[] truth_images = {R.drawable.and_truth, R.drawable.or_truth, R.drawable.xor_truth, R.drawable.nand_truth, R.drawable.nor_truth, R.drawable.xnor_truth, R.drawable.not_truth};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_information);
        intent = getIntent();
        bindViews();
        setGateName();
    }

    public void bindViews() {
        gateNameTextView = (TextView) findViewById(R.id.gate_name_textview);
        gateTruthImageView = (ImageView) findViewById(R.id.gate_truth_table_imageview);
        gateImageView = (ImageView) findViewById(R.id.gate_imageview);
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


}
