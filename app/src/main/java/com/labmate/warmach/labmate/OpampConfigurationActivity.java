package com.labmate.warmach.labmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DecorContentParent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by warmach on 3/8/16.
 */
public class OpampConfigurationActivity extends Activity {
    Intent intent;
    ImageView invertingImageView;
    Spinner configSpinner;
    Button gainButton;
    EditText r1EditText, r1PowerEditText, r2EditText, r2PowerEditText;
    TextView gainTextView, gainFormulaTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opamp_config);
        intent = getIntent();
        bindViews();
        setListeners();
    }

    public void bindViews() {
        gainFormulaTextView = (TextView) findViewById(R.id.gain_formula_textview);
        gainTextView = (TextView) findViewById(R.id.gain_value_textview);
        invertingImageView = (ImageView) findViewById(R.id.config_type_imageview);
        configSpinner = (Spinner) findViewById(R.id.config_spinner);
        if(intent.getStringExtra("config").equals("inverting")) {
            setInverting();
        }
        else if(intent.getStringExtra("config").equals("non-inverting")) {
            setNonInverting();
        }
        gainButton = (Button) findViewById(R.id.gain_button);
        r1EditText = (EditText) findViewById(R.id.r1_editText);
        r1PowerEditText = (EditText) findViewById(R.id.r1_power_editText);
        r2EditText = (EditText) findViewById(R.id.r2_edittext);
        r2PowerEditText = (EditText) findViewById(R.id.r2_power_edittext);
    }

    public void setListeners() {
        configSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                    setInverting();
                else
                    setNonInverting();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setValidations()) {
                    if (configSpinner.getSelectedItemPosition() == 0)
                        calculateInvertingGain();
                    else
                        calculateNonInvertingGain();
                }
            }
        });
    }

    public void setInverting() {
        configSpinner.setSelection(0);
        invertingImageView.setBackgroundResource(R.drawable.inverting);
        gainTextView.setText("");
        gainFormulaTextView.setText("(-R2/R1)");
    }

    public void setNonInverting() {
        configSpinner.setSelection(1);
        invertingImageView.setBackgroundResource(R.drawable.non_inverting);
        gainTextView.setText("");
        gainFormulaTextView.setText("(1 + (R2/R1))");
    }

    public void calculateInvertingGain() {
        String r1 = r1EditText.getText().toString();
        String r1_power = r1PowerEditText.getText().toString();
        String r2 = r2EditText.getText().toString();
        String r2_power = r2PowerEditText.getText().toString();
        DecimalFormat df = new DecimalFormat("#.###");
        if(Float.parseFloat(r1) == 0)
            Toast.makeText(getBaseContext(), "R1 cannot be zero", Toast.LENGTH_LONG).show();
        else {
            float div = Float.parseFloat(r2) / Float.parseFloat(r1);
            int sub = (int) (Float.parseFloat(r2_power) - Float.parseFloat(r1_power));
            String gain = "";
            if (sub == 0)
                gain = "" + df.format(div);
            else
                gain = "" + df.format(div * Math.pow(10, sub));
            gainTextView.setText("- " + gain);
        }
    }

    public void calculateNonInvertingGain() {
        String r1 = r1EditText.getText().toString();
        String r1_power = r1PowerEditText.getText().toString();
        String r2 = r2EditText.getText().toString();
        String r2_power = r2PowerEditText.getText().toString();
        DecimalFormat df = new DecimalFormat("#.###");
        if(Float.parseFloat(r1) == 0)
            Toast.makeText(getBaseContext(), "R1 cannot be zero", Toast.LENGTH_LONG).show();
        else {
            float div = Float.parseFloat(r2) / Float.parseFloat(r1);
            int sub = (int) (Float.parseFloat(r2_power) - Float.parseFloat(r1_power));
            float temp = (float) (div * Math.pow(10, sub));
            float gain = 1 + temp;
            gainTextView.setText("" + df.format(gain));
        }
    }

    public boolean setValidations() {
        if(r1EditText.getText().toString().length() == 0 || r1PowerEditText.getText().toString().length() == 0 ||
                r2EditText.getText().toString().length() == 0 || r2PowerEditText.getText().toString().length() == 0)
        {
            Toast.makeText(getBaseContext(), "Please enter the values", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
