package com.example.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class item{
    String title;
    int price;
    int quantity;
    String pid;

    item(String t, int p, int q, String id) {
        title = t;
        price = p;
        quantity = q;
        pid = id;
    }
}


public class cartpage extends AppCompatActivity {
    ListView listView;

    DatabaseReference mDatabase;
    String phoneno;
    ArrayList<item> items;

    Button checkout;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartpage);

        checkout = findViewById(R.id.checkout);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent i = getIntent();
        if(i.hasExtra("phoneno")){
            phoneno = i.getStringExtra("phoneno");
        }
        items = new ArrayList<>();

        gettingDatafromDb();

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t = calculateBill()+"";
                Intent i = new Intent(cartpage.this, Checkout.class);
                System.out.println("price "+t);
                i.putExtra("tp", t);
                startActivity(i);
            }
        });


    }
    public int calculateBill(){
        int totalPrice =0;

        for(item i: items){
            int priceforOne = i.quantity*i.price;
            totalPrice += priceforOne;
        }
        return totalPrice;

    }






    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.cartitem, null);

            TextView tview = (TextView) convertView.findViewById(R.id.item);

            tview.setText(items.get(position).title);

            TextView q = (TextView) convertView.findViewById(R.id.quantity);
            String temp1 = items.get(position).quantity+"";
            q.setText(temp1);
            TextView p = (TextView) convertView.findViewById(R.id.price);
            String temp2 = items.get(position).price+"";
            p.setText(temp2);


            Button del = (Button) convertView.findViewById(R.id.delete);

            del.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    removing(position);
                }
            });

            return convertView;
        }
    }



    public void gettingDatafromDb(){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.child("Cart List").child(phoneno).child("Products").getChildren()){

                    String t = data.child("pname").getValue().toString();
                    int p= Integer.parseInt(data.child("price").getValue().toString());
                    int q= Integer.parseInt(data.child("quantity").getValue().toString());
                    String id = data.child("pid").getValue().toString();
                    item i = new item(t,p,q,id);
                    items.add(i);
                }
                listView = findViewById(R.id.selectedproducts);
                CustomAdapter customAdapter = new CustomAdapter();
                listView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void removing(int index){
        System.out.println("iam here33  "+mDatabase.child("Cart List").child("phoneno") );
        mDatabase.child("Cart List").child(phoneno).child("Products").child(items.get(index).pid).removeValue();
        System.out.println("deleted value is"+mDatabase.child("Cart List").child(phoneno).child("Products").child(items.get(index).pid));
        items=new ArrayList<>();
        gettingDatafromDb();

    }

}
