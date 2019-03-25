package com.example.msitplacements;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewSelectedActivity extends AppCompatActivity {
    private String parentDbName = "Selected Students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected);
        final TextView text = (TextView) findViewById(R.id.selectedStudents);
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = "";
                String str1 = "";
                for (DataSnapshot data : dataSnapshot.child("Selected Students").getChildren()) {
                    str += (data.child("Id").getValue().toString());
                    str1 += " Student Name:" + data.child("Name").getValue().toString() + "\n Company Placed: " + data.child("Company Placed").getValue().toString() + "\n\n";
                    text.setText(str1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
