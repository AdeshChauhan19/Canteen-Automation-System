package com.example.canteenautomationsystem.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.Food_Display.Datum;
import com.example.canteenautomationsystem.model.LoginInterface;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.VERTICAL;

public class Materials extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food, null);
    }

    private Button add;
    private RecyclerView recyclerView;
    private MaterialsAdapter adapter;
    private List<Datum> materialData = new ArrayList<>();

    @SuppressLint("WrongConstant")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.materials_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), VERTICAL,false));
//        if (quantities.getText().toString().isEmpty())
//        {
//            adds.setEnabled(false);
//        }
        getMaterialData(getActivity().getIntent().getStringExtra("id"),"1");
        adapter = new MaterialsAdapter(getActivity().getApplicationContext(), materialData);
        recyclerView.setAdapter(adapter);

    }
    private void getMaterialData(String id,String category) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(LoginInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginInterface api = retrofit.create(LoginInterface.class);
        Call<JsonObject> call = api.food(id,category);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    JsonObject jsonObject = response.body();
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = new JSONObject(jsonObject.toString());
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Datum datum = new Datum();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            datum.setId(jsonObject2.optString("id"));
                            datum.setName(jsonObject2.optString("name"));
                            datum.setPrice(jsonObject2.optString("price"));
                            datum.setImage(jsonObject2.optString("image"));
                            datum.setStock(jsonObject2.optString("stock"));
//                            datum.setId(jsonObject2.optString("add"));
                            materialData.add(datum);
                            Log.d("response", jsonObject2.optString("image"));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new MaterialsAdapter(getActivity().getApplicationContext(), materialData);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}