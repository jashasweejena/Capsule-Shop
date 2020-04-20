package com.example.capsule_shop_final.Login;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.capsule_shop_final.Inventory.AddMedicinesActivity;
import com.example.capsule_shop_final.Orders.MyOrders;
import com.example.capsule_shop_final.Orders.Order;
import com.example.capsule_shop_final.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class LoggedInActivity extends AppCompatActivity {
    final private static String TAG = "LoggedInActivity";
    final private static int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 10;

    private Button ordersButton;
    private Button inventoryButton;


    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();
        handle();

        trackChanges();

        handlePermissions();

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

    private void initialize() {
        ordersButton = findViewById(R.id.btn_my_orders);
        inventoryButton = findViewById(R.id.btn_update_inventory);

        uid = FirebaseAuth.getInstance().getUid();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("shop");
    }


    private void trackChanges() {
        DatabaseReference ref = myRef.child(uid).child("orders");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    Order order = child.getValue(Order.class);

                    assert order != null;
                    String notificationString = String.format("New order from %s", order.getName());
                    sendNotification("New Order!!", notificationString);

                    Toast.makeText(LoggedInActivity.this, order.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //    TODO:- ADD HEADS UP POPUP
    private void sendNotification(String title, String body) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        int requestID = (int) System.currentTimeMillis();

        Intent notificationIntent = new Intent(getApplicationContext(), MyOrders.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_capsule_blue)
                .setAutoCancel(true)
                .setContentText(body)
                .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }

    private void handlePermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            }
        } else {
            Toast.makeText(this, "Permission has already been granted", Toast.LENGTH_SHORT).show();
        }
    }

}
