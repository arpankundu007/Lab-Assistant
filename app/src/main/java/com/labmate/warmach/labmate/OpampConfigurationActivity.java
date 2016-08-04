package com.labmate.warmach.labmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by warmach on 3/8/16.
 */
public class OpampConfigurationActivity extends Activity {
    Intent intent;
    TextView configTypeTextView;
    ImageView invertingImageView;
    Spinner configSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opamp_config);
        intent = getIntent();
        bindViews();
        setListeners();
    }

    public void bindViews() {
        invertingImageView = (ImageView) findViewById(R.id.config_type_imageview);
        configSpinner = (Spinner) findViewById(R.id.config_spinner);
        if(intent.getStringExtra("config").equals("inverting"))
            setInverting();
        else if(intent.getStringExtra("config").equals("non-inverting"))
            setNonInverting();
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
    }

    public void setInverting() {
        configSpinner.setSelection(0);
        invertingImageView.setBackgroundResource(R.drawable.inverting);
    }

    public void setNonInverting() {
        configSpinner.setSelection(1);
        invertingImageView.setBackgroundResource(R.drawable.non_inverting);
    }
}
