package com.example.capsule_shop_final.Inventory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.capsule_shop_final.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddMedicinesActivity extends AppCompatActivity {
    final private static String TAG = "AddMedicinesActivity";

    private MutableLiveData<List<Medicine>> mutableFetchedList = new MutableLiveData<>();

    private EditText descEditText;
    private EditText priceEditText;
    private EditText qtyEditText;
    private Button addToListBtn;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();

        final FloatingActionButton fab = findViewById(R.id.fab);


        getCurrentList().observe(this, new Observer<List<Medicine>>() {

            @Override
            public void onChanged(final List<Medicine> medicines) {
                try {

                    //Add medicines to list
                    addToListBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String desc = descEditText.getText().toString();
                            int price = Integer.parseInt(priceEditText.getText().toString());
                            int qty = Integer.parseInt(qtyEditText.getText().toString());

                            Medicine medicine = new Medicine(desc, price, qty);

                            //Update the fetched list with new data
                            medicines.add(medicine);

                            descEditText.setText("");
                            qtyEditText.setText("");
                            priceEditText.setText("");
                        }
                    });

                    //Update list and push to Firebase
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (medicines != null && medicines.size() > 0) {
                                writeData(medicines);
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d(TAG, "onChanged: " + e);
                    Toast.makeText(AddMedicinesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(String.format("shop/%s", uid));
    }

    private MutableLiveData<List<Medicine>> getCurrentList() {
        final List<Medicine> medList = new ArrayList<>();

        myRef.child("medicines").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Medicine medicine = child.getValue(Medicine.class);
                    medList.add(medicine);
                }
                mutableFetchedList.postValue(medList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return mutableFetchedList;
    }

}
