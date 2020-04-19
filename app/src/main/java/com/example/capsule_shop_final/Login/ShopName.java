package com.example.capsule_shop_final.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.capsule_shop_final.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShopName extends AppCompatActivity {
    final private static String TAG = "ShopName";

    private EditText inputShop;
    private Button buttonShop;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_name);

        initialize();

        final String uid = FirebaseAuth.getInstance().getUid();
        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = inputShop.getText().toString();
                myRef.child(uid).child("name").setValue(name);

                Intent intent = new Intent(ShopName.this, LoggedInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialize(){
        inputShop = findViewById(R.id.input_shop_name);
        buttonShop = findViewById(R.id.button_shop_done);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("shop");
    }
}
