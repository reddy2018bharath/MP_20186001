package com.example.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.v4.content.ContextCompat.startActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Product> titles;
    HashMap<String, Integer> positions;
    Context mContext;
    String phoneNumber, password;

    public RecyclerViewAdapter(ArrayList<Product> e, Context c, HashMap p){
        titles = e;
        mContext = c;
        phoneNumber = productsInformation.phoneno;
        password = productsInformation.pwd;
        positions = p;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem,viewGroup, false);
        ViewHolder holder = new ViewHolder(view);


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
//        Log.d("in",titles.get(i).productName);
        viewHolder.title.setText(titles.get(i).productName);
        viewHolder.price.setText(titles.get(i).price);
        String t = "http://msitmp.herokuapp.com"+titles.get(i).url;
        Picasso.with(mContext).load(t).into(viewHolder.image);



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent newIntent = new Intent(mContext, itemDescription.class);

                        newIntent.putExtra("title",titles.get(i).productName);
                        newIntent.putExtra("price", titles.get(i).price);
                        newIntent.putExtra("Id",titles.get(i).productId);
                        newIntent.putExtra("quantity", titles.get(i).quantity);
                        newIntent.putExtra("desc", titles.get(i).desc);
                        newIntent.putExtra("url", "http://msitmp.herokuapp.com"+titles.get(i).url);
                        newIntent.putExtra("phoneno",phoneNumber);
                        newIntent.putExtra("password",password);
                        newIntent.putExtra("positions", positions);
                        mContext.startActivity(newIntent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return titles.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView title;
        ImageView image;
        TextView price;


        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            price =itemView.findViewById(R.id.price);
//            url = getIntent().getStringExtra("url");
//            Picasso.with(RecyclerViewAdapter.this).load().into(image);
            context = itemView.getContext();

        }
    }
}
