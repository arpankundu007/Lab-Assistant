package com.labmate.warmach.labmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by warmach on 15/8/16.
 */
public class GatesActivity extends Activity {
    RelativeLayout andGate, orGate, xorGate, nandGate, norGate, xnorGate, notGate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gates);
        bindViews();
        setListeners();
    }

    public void bindViews() {
        andGate = (RelativeLayout) findViewById(R.id.and_gate);
        orGate = (RelativeLayout) findViewById(R.id.or_gate);
        xorGate = (RelativeLayout) findViewById(R.id.xor_gate);
        nandGate = (RelativeLayout) findViewById(R.id.nand_gate);
        norGate = (RelativeLayout) findViewById(R.id.nor_gate);
        xnorGate = (RelativeLayout) findViewById(R.id.xnor_gate);
        notGate = (RelativeLayout) findViewById(R.id.not_gate);

    }

    public void setListeners() {
        andGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAndGate();
            }
        });

        orGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOrGate();
            }
        });

        xorGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setXorGate();
            }
        });

        nandGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNandGate();
            }
        });

        norGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNorGate();
            }
        });

        xnorGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setXnorGate();
            }
        });

        notGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotGate();
            }
        });
    }

    public void setAndGate(){
        Intent intent = new Intent(GatesActivity.this, GateInformationActivity.class);
        intent.putExtra("gate_type", "and");
        startActivity(intent);
    }

    public void setOrGate() {
        Intent intent = new Intent(GatesActivity.this, GateInformationActivity.class);
        intent.putExtra("gate_type", "or");
        startActivity(intent);
    }

    public void setXorGate() {
        Intent intent = new Intent(GatesActivity.this, GateInformationActivity.class);
        intent.putExtra("gate_type", "xor");
        startActivity(intent);
    }

    public void setNandGate() {
        Intent intent = new Intent(GatesActivity.this, GateInformationActivity.class);
        intent.putExtra("gate_type", "nand");
        startActivity(intent);
    }

    public void setNorGate() {
        Intent intent = new Intent(GatesActivity.this, GateInformationActivity.class);
        intent.putExtra("gate_type", "nor");
        startActivity(intent);
    }

    public void setXnorGate() {
        Intent intent = new Intent(GatesActivity.this, GateInformationActivity.class);
        intent.putExtra("gate_type", "xnor");
        startActivity(intent);
    }

    public void setNotGate() {
        Intent intent = new Intent(GatesActivity.this, GateInformationActivity.class);
        intent.putExtra("gate_type", "not");
        startActivity(intent);
    }
}
