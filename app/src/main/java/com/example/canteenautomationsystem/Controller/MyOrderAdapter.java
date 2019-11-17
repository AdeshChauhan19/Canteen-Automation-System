package com.example.canteenautomationsystem.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.AllOrdersDisplay.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<Datum> myorderlist;

    public MyOrderAdapter(Context context, List<Datum> myorderlist) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.myorderlist=myorderlist;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MyOrderAdapter.ViewHolder(inflater.inflate(R.layout.order_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder viewHolder, int i)
    {
        viewHolder.name.setText(myorderlist.get(i).getFoodName());
        viewHolder.quantity.setText(myorderlist.get(i).getQuantity());
        viewHolder.total_price.setText(myorderlist.get(i).getTotalPrice());
        Picasso.get().load(myorderlist.get(i).getImage()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return myorderlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name,total_price,quantity;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_order);
            name=itemView.findViewById(R.id.name_card);
            total_price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
        }
    }
}

