package com.example.msitplacements;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompanyDetails extends AppCompatActivity {
    private String parentDbName = "Company";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        final TextView text = (TextView) findViewById(R.id.companyDet);
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = "";
                for(DataSnapshot data : dataSnapshot.child("Company").getChildren()) {

                    str += (" Company Name: " + data.child("Company Name").getValue().toString() + "\n Company Type: " + data.child("Company Type").getValue().toString() + "\n Headquarters: " + data.child("Headquarters").getValue().toString() + "\n\n");
                    text.setText(str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
