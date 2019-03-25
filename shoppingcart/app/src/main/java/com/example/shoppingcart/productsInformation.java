package com.example.shoppingcart;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class productsInformation extends AppCompatActivity {

    private static String data;
    private ArrayList<Product> items = new ArrayList<>();
    HashMap<String, Integer> positions= new HashMap<>();
    static String phoneno, pwd;
    DatabaseReference mDatabase;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_information);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent i = getIntent();
        if(i.hasExtra("phone")){
            phoneno = i.getStringExtra("phone");
        }
        if(i.hasExtra("pwd")){
            pwd = i.getStringExtra("pwd");
        }

        gettingDatafromDb();

    }




    public void gettingDatafromDb(){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(int i=0;i<122;i++){

                    final String t =i+"";

                    DataSnapshot data = dataSnapshot.child("ProductCollection").child(t);

                    positions.put(data.child("Name").toString(), i);

                    Product p = new Product(data.child("ProductId").getValue().toString(), data.child("Name").getValue().toString(),data.child("Price").getValue().toString(), data.child("Quantity").getValue().toString(),data.child("Description").getValue().toString(),
                            data.child("ProductPicUrl").getValue().toString());
                    items.add(p);
                }
                callingRecyclerView();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void callingRecyclerView(){


        RecyclerView recyclerView = findViewById(R.id.recycler_view);


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items,this, positions);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
