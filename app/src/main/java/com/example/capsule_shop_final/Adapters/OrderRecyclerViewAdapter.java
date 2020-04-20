package com.example.capsule_shop_final.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_shop_final.Location.LocationActivity;
import com.example.capsule_shop_final.Orders.Order;
import com.example.capsule_shop_final.Orders.OrderAccepted;
import com.example.capsule_shop_final.R;

import java.util.List;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.Viewholder> {

    private List<Order> ordersList;
    private Context mContext;
    private static final String TAG = "OrderRecyclerViewAdapter";

    public OrderRecyclerViewAdapter(List<Order> ordersList, Context mContext) {
        this.ordersList = ordersList;
        this.mContext = mContext;

        Log.d(TAG, "OrderRecyclerViewAdapter: " + "called");
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_orders, parent, false);
        OrderRecyclerViewAdapter.Viewholder vh = new Viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TextView name = holder.name;
        TextView address = holder.address;

        name.setText(ordersList.get(position).getName());
        address.setText(ordersList.get(position).getAddress());

        Log.d(TAG, "onBindViewHolder: " + ordersList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        private TextView address;

        //TODO Fix the meds list
        private TextView medsList;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.order_name);
            address = itemView.findViewById(R.id.order_address);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context mContext = view.getContext();
            String customerName = ordersList.get(getAdapterPosition()).getName();
            Intent intent = new Intent(mContext, LocationActivity.class);
            intent.putExtra(mContext.getString(R.string.shop_name_key), customerName);
            mContext.startActivity(intent);
        }
    }

}
