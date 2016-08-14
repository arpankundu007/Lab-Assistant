package com.labmate.warmach.labmate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by warmach on 14/8/16.
 */
public class Decoder38Activity extends Activity {
    Spinner decoderTypeSpinner;
    ImageView decoderImageView;
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
    }

    public void setListeners() {
        decoderTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                    decoderImageView.setBackgroundResource(R.drawable.decoder_block);
                else
                    decoderImageView.setBackgroundResource(R.drawable.encoder_block);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
