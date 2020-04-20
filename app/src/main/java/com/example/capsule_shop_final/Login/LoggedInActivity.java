package com.example.capsule_shop_final.Login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.capsule_shop_final.Inventory.AddMedicinesActivity;
import com.example.capsule_shop_final.Orders.MyOrders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle("This is my title")
                .setSmallIcon(R.drawable.ic_capsule_blue)
                .setAutoCancel(true)
                .setContentText("This is my text");

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }

    private void handle() {
        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggedInActivity.this, MyOrders.class);
                startActivity(intent);

//              For testing only
                NotificationCompat.Builder builder = new NotificationCompat.Builder(LoggedInActivity.this, "channel_id")
                        .setSmallIcon(android.R.drawable.star_on)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(LoggedInActivity.this);

// notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, builder.build());

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
