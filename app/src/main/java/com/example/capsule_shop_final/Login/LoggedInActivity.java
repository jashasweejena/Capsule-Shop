package com.example.capsule_shop_final.Login;

import android.content.Intent;
import android.os.Bundle;

import com.example.capsule_shop_final.Inventory.AddMedicinesActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.capsule_shop_final.R;

public class LoggedInActivity extends AppCompatActivity {
    final private static String TAG = "LoggedInActivity";

    private Button ordersButton;
    private Button inventoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
        handle();
    }

    private void handle() {
        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO order page
            }
        });
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggedInActivity.this, AddMedicinesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialize(){
        ordersButton = findViewById(R.id.btn_my_orders);
        inventoryButton = findViewById(R.id.btn_update_inventory);
    }

}
