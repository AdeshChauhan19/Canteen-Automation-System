//package com.example.canteenautomationsystem.Controller;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//
//import com.example.canteenautomationsystem.R;
//import com.example.canteenautomationsystem.Response.Today_Orders.Datum;
//import com.example.canteenautomationsystem.model.LoginInterface;
//import com.google.gson.JsonObject;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class TodayOrder extends Fragment
//{
//    private void getTodayOrderData(String id)
//    {
//        Retrofit retrofit = new Retrofit
//                .Builder()
//                .baseUrl(LoginInterface.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        LoginInterface api = retrofit.create(LoginInterface.class);
//        Call<JsonObject> call = api.todayorder(id,"1");
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.body()!=null)
//                {
//                    JsonObject jsonObject=response.body();
//                    JSONObject jsonObject1=null;
//                    try {
//                        jsonObject1=new JSONObject(jsonObject.toString());
//                        JSONArray jsonArray=jsonObject1.getJSONArray("data");
//                        for (int i = jsonArray.length()-1; i >= 0; i--)
//                        {
//                            Datum datum=new Datum();
//                            JSONObject jsonObject2=jsonArray.getJSONObject(i);
//                            datum.setFoodName(jsonObject2.optString("food_name"));
//                            datum.setTotalPrice(jsonObject2.optString("total_price"));
//                            datum.setQuantity(jsonObject2.optString("quantity"));
//                            TodayOrderData.add(datum);
//                            Log.e("ERROR",jsonObject2.optString("material_name"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    adapter=new TodayOrderAdapter(getActivity().getApplicationContext(),TodayOrderData);
//                    adapter.notifyDataSetChanged();
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.today_cart, null);
//    }
//
//    private RecyclerView recyclerView;
//    private TodayOrderAdapter adapter;
//    private List<Datum> TodayOrderData=new ArrayList<>();
//    private ProgressBar progressBar;
//    @SuppressLint("WrongConstant")
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerView = view.findViewById(R.id.today_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), VERTICAL,false));
//        getTodayOrderData(getActivity().getIntent().getStringExtra("id"));
//        adapter=new TodayOrderAdapter(getActivity().getApplicationContext(),TodayOrderData);
//        recyclerView.setAdapter(adapter);
//    }
//    void showProgressView() {
//        progressBar.setVisibility(View.VISIBLE);
//    }
//
//    void hideProgressView() {
//        progressBar.setVisibility(View.INVISIBLE);
//    }
//}
