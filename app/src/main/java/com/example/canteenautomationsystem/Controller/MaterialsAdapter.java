package com.example.canteenautomationsystem.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.Cart_Payment.CartPayment;
import com.example.canteenautomationsystem.Response.Food_Display.Datum;
import com.example.canteenautomationsystem.model.Data;
import com.example.canteenautomationsystem.model.LoginInterface;
import com.example.canteenautomationsystem.model.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ConcurrentModificationException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Datum> materiallist;

    public MaterialsAdapter(Context context, List<Datum> materiallist) {
        this.context=context;
        this.inflater = LayoutInflater.from(context);
        this.materiallist = materiallist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return    new ViewHolder(inflater.inflate(R.layout.food_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.name.setText(materiallist.get(i).getName());
        viewHolder.price.setText(materiallist.get(i).getPrice());
        viewHolder.id.setText(materiallist.get(i).getId());
        Picasso.get().load(materiallist.get(i).getImage()).into(viewHolder.image);
        viewHolder.quantity.setText("");
        viewHolder.add.setVisibility(View.VISIBLE);
        viewHolder.quantity.setVisibility(View.VISIBLE);
//        viewHolder.add.setEnabled(true);
        //http://172.16.88.109/omsaibuildingmaterials/uploads/1565552048.png
        Log.e("Tag",materiallist.get(i).getImage());
        SharedPrefManager sharedPrefManager=new SharedPrefManager(context);
        final String user_id=sharedPrefManager.getString("id");
//        Toast.makeText(context,user_id,Toast.LENGTH_LONG).show();
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view)
            {
                Retrofit retrofit=new Retrofit
                        .Builder()
                        .baseUrl(LoginInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginInterface api=retrofit.create(LoginInterface.class);
                Call<CartPayment> responseCall=api.cart(viewHolder.id.getText().toString(),viewHolder.quantity.toString().trim(),"0","0",user_id);
                responseCall.enqueue(new Callback<CartPayment>() {
                    @Override
                    public void onResponse(Call<CartPayment> call, Response<CartPayment> response)
                    {

                        Toast.makeText(context,"Added to the Cart", Toast.LENGTH_SHORT).show();
                        view.setVisibility(View.GONE);
                        viewHolder.quantity.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<CartPayment> call, Throwable t)
                    {
                        Toast.makeText(context,"FAIL",Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return materiallist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price,id,stock;
        EditText quantity;
        Button add;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pro_img);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            add = itemView.findViewById(R.id.Add);
            quantity = itemView.findViewById(R.id.quantity);
            id=itemView.findViewById(R.id.material_id);
            stock=itemView.findViewById(R.id.stock);
        }
    }
}
