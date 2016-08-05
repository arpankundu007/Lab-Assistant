package com.labmate.warmach.labmate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by warmach on 5/8/16.
 */
public class Timer555Activity extends Activity {
    Spinner timerModeSPinner;
    ImageView timerModeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_555_details);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        timerModeSPinner = (Spinner) findViewById(R.id.timer_mode_spinner);
        timerModeImageView = (ImageView) findViewById(R.id.timer_mode_imageview);
    }

    public void setListeners() {
        timerModeSPinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                    setAstableMode();
                else
                    setMonoStableMode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setAstableMode(){
        timerModeImageView.setBackgroundResource(R.drawable.timer_555_config);
    }

    public void setMonoStableMode() {
        timerModeImageView.setBackgroundResource(R.drawable.timer_555_monostable);
    }
}
