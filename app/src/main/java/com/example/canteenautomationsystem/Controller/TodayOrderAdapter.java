package com.example.canteenautomationsystem.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.Today_Orders.Datum;

import java.util.List;

public class TodayOrderAdapter extends RecyclerView.Adapter<TodayOrderAdapter.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<Datum> todaylist;

    public TodayOrderAdapter(Context context,List<Datum> todaylist)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.todaylist = todaylist;
    }

    @NonNull
    @Override
    public TodayOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new TodayOrderAdapter.ViewHolder(inflater.inflate(R.layout.fragment_my_order, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodayOrderAdapter.ViewHolder viewHolder, int i)
    {
        viewHolder.name.setText(todaylist.get(i).getFoodName());
        viewHolder.quantity.setText(todaylist.get(i).getQuantity());
        viewHolder.total_price.setText(todaylist.get(i).getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return todaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name,total_price,quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.myorder_name);
            total_price=itemView.findViewById(R.id.myorder_price);
            quantity=itemView.findViewById(R.id.myorder_quantity);
        }
    }
}

