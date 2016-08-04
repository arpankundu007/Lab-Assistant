package com.labmate.warmach.labmate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by warmach on 3/8/16.
 */
public class AnalogComponentsActivity extends Activity {
    RelativeLayout opampRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analog);
        bindViews();
        setListeners();
    }

    public void bindViews(){
        opampRelativeLayout = (RelativeLayout) findViewById(R.id.opamp_relativeLayout);
    }

    public void setListeners() {
        opampRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOpampPopup();
            }
        });
    }

    public void showOpampPopup(){
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.opamp_layout, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button inverting = (Button) view.findViewById(R.id.inverting_button);
                Button nonInverting = (Button) view.findViewById(R.id.non_inverting_button);
                inverting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        navigateToOpampConfig("inverting");
                    }
                });
                nonInverting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        navigateToOpampConfig("non-inverting");
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void navigateToOpampConfig(String config){
        Intent intent = new Intent(AnalogComponentsActivity.this, OpampConfigurationActivity.class);
        intent.putExtra("config", config);
        startActivity(intent);
    }
}
