package com.example.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shoppingcart.Model.cart;
import com.example.shoppingcart.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class itemDescription extends AppCompatActivity {

    TextView ptitle, pprice, pdesc;
    String title, phoneno, password;
//    String price;
    int maxq;
    String id;
    String description,url,price;
    Button cart, cartpage;
    ElegantNumberButton pquantity;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.itemdescription);

       ptitle = findViewById(R.id.producttitle);
       pprice = findViewById(R.id.price);
       pdesc = findViewById(R.id.desc);
       pquantity =  findViewById(R.id.quantity);
       cart = findViewById(R.id.cart);
       cartpage = findViewById(R.id.cartpage);
       image = findViewById(R.id.img);


        cartpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(itemDescription.this, cartpage.class);
                i.putExtra("phoneno", phoneno);
                startActivity(i);
            }
        });

       cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               insertingintoDb();
           }
       });


        if(getIntent().hasExtra("phoneno")){
            phoneno = getIntent().getStringExtra("phoneno");

        }
        if(getIntent().hasExtra("quantity")){
            String q = getIntent().getStringExtra("quantity");

            maxq = Integer.parseInt(q);


        }

        if(getIntent().hasExtra("password")){
            password = getIntent().getStringExtra("password");

        }
        if(getIntent().hasExtra("url")){
            url = getIntent().getStringExtra("url");
            Picasso.with(itemDescription.this).load(url).into(image);

        }

        if(getIntent().hasExtra("Id")){
            id = getIntent().getStringExtra("Id");

        }

        if(getIntent().hasExtra("title")){
            title = getIntent().getStringExtra("title");

            ptitle.setText(title);

        }
        if(getIntent().hasExtra("price")){
            price = getIntent().getStringExtra("price");

            pprice.setText(price);

        }
        if(getIntent().hasExtra("desc")){
            description = getIntent().getStringExtra("desc");

            pdesc.setText(description);


        }
        pquantity.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = pquantity.getNumber();
                int t = Integer.parseInt(num);
                if(t>maxq){
                    pquantity.setNumber(maxq+"");
                }
            }
        });

    }

    public void insertingintoDb(){


        final DatabaseReference cartListRef;
        cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", id);
        cartMap.put("pname", title);
        cartMap.put("price", price);
        cartMap.put("quantity", pquantity.getNumber());
        cartListRef.child(phoneno).child("Products").child(id)
                .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(itemDescription.this, "data inserted", Toast.LENGTH_SHORT).show();
                }else{
                    System.out.println("error");
                }
            }
        });

    }





}
