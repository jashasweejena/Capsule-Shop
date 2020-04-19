package com.example.capsule_shop_final.Inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.capsule_shop_final.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddMedicinesActivity extends AppCompatActivity {
    final private static String TAG = "AddMedicinesActivity";

    private EditText descEditText;
    private EditText priceEditText;
    private EditText qtyEditText;
    private Button addToListBtn;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String uid;
    private List<Medicine> medsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();

        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc = descEditText.getText().toString();
                int price = Integer.parseInt(priceEditText.getText().toString());
                int qty = Integer.parseInt(qtyEditText.getText().toString());

                Medicine medicine = new Medicine(desc, price, qty);
                medsList.add(medicine);

                descEditText.setText("");
                qtyEditText.setText("");
                priceEditText.setText("");
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(medsList != null && medsList.size() > 0){
                    writeData(medsList);
                }
            }
        });
    }

    private void writeData(List<Medicine> med1) {
        myRef.child("medicines").setValue(med1);
    }


    private void initialize() {
        descEditText = findViewById(R.id.input_med_desc);
        priceEditText = findViewById(R.id.input_med_price);
        qtyEditText = findViewById(R.id.input_med_qty);
        addToListBtn = findViewById(R.id.btn_med_listadd);

        uid = FirebaseAuth.getInstance().getUid();

        medsList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(String.format("shop/%s", uid));
    }

}
