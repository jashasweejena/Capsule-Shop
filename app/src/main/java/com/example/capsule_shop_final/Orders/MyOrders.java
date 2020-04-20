package com.example.capsule_shop_final.Orders;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_shop_final.Adapters.OrderRecyclerViewAdapter;
import com.example.capsule_shop_final.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyOrders extends AppCompatActivity {
    final private static String TAG = "MyOrders";
    private static MutableLiveData<ArrayList<Order>> mutableOrders = new MutableLiveData<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String uid;
    private RecyclerView rv;
    private OrderRecyclerViewAdapter adapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        orderList = new ArrayList<>();
//        Medicine medicine = new Medicine();
//        medicine.setDesc("desc");
//        medicine.setPrice(12);
//        medicine.setQty(124);
//        List<Medicine> medsList = new ArrayList<>();
//        medsList.add(medicine);
//        orderList.add(new Order("name", "address", medsList));

        initialize();

        recyclerView();

        refresh();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_refresh:
                refresh();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("shop");

        uid = FirebaseAuth.getInstance().getUid();


    }

    private void recyclerView() {
        rv = findViewById(R.id.order_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "recyclerView: " + orderList);
    }

    private MutableLiveData<ArrayList<Order>> fetchOrders() {
        final ArrayList<Order> mOrders = new ArrayList<>();
        DatabaseReference ref = myRef.child(uid).child("orders");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    mOrders.add(order);
                }

                mutableOrders.postValue(mOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mutableOrders;
    }

    private void refresh(){
        recyclerView();

        fetchOrders().observe(this, new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(ArrayList<Order> orders) {
                rv.setAdapter(new OrderRecyclerViewAdapter(orders, MyOrders.this));
//                Log.d(TAG, "onChanged: " + orders.si);
            }
        });
    }

}
