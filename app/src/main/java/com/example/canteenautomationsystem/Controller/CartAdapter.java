package com.example.canteenautomationsystem.Controller;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.AllOrdersDisplay.Datum;
import com.example.canteenautomationsystem.Response.Cart_Payment.CartPayment;
import com.example.canteenautomationsystem.Response.Remove_Cart.RemoveCart;
import com.example.canteenautomationsystem.model.LoginInterface;
import com.example.canteenautomationsystem.model.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<Datum> cartlist;
    //    private int k=0;
    public CartAdapter(Context context, List<Datum> cartlist) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.cartlist = cartlist;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new CartAdapter.ViewHolder(inflater.inflate(R.layout.card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder viewHolder, int i)
    {
        //viewHolder.image.setImageURI();
        viewHolder.name.setText(cartlist.get(i).getFoodName());
        viewHolder.quantity.setText(cartlist.get(i).getQuantity());
        viewHolder.total_price.setText(cartlist.get(i).getTotalPrice());
        viewHolder.price.setText(cartlist.get(i).getTotalPrice());
        Picasso.get().load(cartlist.get(i).getImage()).into(viewHolder.image);
//        if (k!=i)
//        {
//                    viewHolder.confirm.setVisibility(View.VISIBLE);
//                    viewHolder.remove.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            viewHolder.confirm.setVisibility(View.INVISIBLE);
//            viewHolder.remove.setVisibility(View.INVISIBLE);
//        }
        SharedPrefManager sharedPrefManager=new SharedPrefManager(context);
        final String user_id=sharedPrefManager.getString("id");
        viewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Retrofit retrofit=new Retrofit
                        .Builder()
                        .baseUrl(LoginInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginInterface api=retrofit.create(LoginInterface.class);
                Call<CartPayment> responseCall=api.cart(viewHolder.material_id.getText().toString(),viewHolder.quantity.getText().toString(),"1",viewHolder.cart_id.getText().toString(),user_id);
                responseCall.enqueue(new Callback<CartPayment>() {
                    @Override
                    public void onResponse(Call<CartPayment> call, Response<CartPayment> response) {
                        Toast.makeText(context,"OWNER CALL YOU FOR CONFIRMATION",Toast.LENGTH_SHORT).show();
                        view.setVisibility(View.INVISIBLE);
//                        viewHolder.remove.setVisibility(View.INVISIBLE);
                        viewHolder.remove.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onFailure(Call<CartPayment> call, Throwable t)
                    {
                        Toast.makeText(context,"FAIL",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Retrofit retrofit=new Retrofit
                        .Builder()
                        .baseUrl(LoginInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginInterface api=retrofit.create(LoginInterface.class);
                Call<RemoveCart> RemoveCartCall=api.removecart(viewHolder.cart_id.getText().toString());
                RemoveCartCall.enqueue(new Callback<RemoveCart>() {
                    @Override
                    public void onResponse(Call<RemoveCart> call, Response<RemoveCart> response)
                    {
                        Toast.makeText(context,"Cart Removed",Toast.LENGTH_SHORT).show();
                        view.setVisibility(View.GONE);
                        viewHolder.confirm.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<RemoveCart> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private TextView name,total_price,quantity,cart_id,material_id,price;
        private Button confirm,remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.cart_name);
            total_price=itemView.findViewById(R.id.cart_total);
            quantity=itemView.findViewById(R.id.cart_quantity);
            price=itemView.findViewById(R.id.cart_price);
            confirm=itemView.findViewById(R.id.confirm_btn);
            remove=itemView.findViewById(R.id.remove_btn);
                image=itemView.findViewById(R.id.cart_image);

        }


   
    }
}
