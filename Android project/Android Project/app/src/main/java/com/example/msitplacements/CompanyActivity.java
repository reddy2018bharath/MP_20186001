package com.example.msitplacements;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CompanyActivity extends AppCompatActivity {
    private EditText inputName, inputType, inputLocation;
    private Button btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        inputName = (EditText) findViewById(R.id.editText10);
        inputType = (EditText) findViewById(R.id.editText14);
        inputLocation = (EditText) findViewById(R.id.editText15);
        btnadd = (Button) findViewById(R.id.button18);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String type = inputType.getText().toString();
                String location = inputLocation.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter the Company Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(type)) {
                    Toast.makeText(getApplicationContext(), "Enter the Company Type!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(location)) {
                    Toast.makeText(getApplicationContext(), "Enter the Company's Location!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    ValidateId(name, type, location);
                }
            }
            private void ValidateId(final String name, final String type, final String location) {
                final DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.child("Company").child(name).exists())) {
                            HashMap<String, Object> companydataMap = new HashMap<>();
                            companydataMap.put("Company Name", name);
                            companydataMap.put("Company Type", type);
                            companydataMap.put("Headquarters", location);

                            mDatabase.child("Company").child(name).updateChildren(companydataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CompanyActivity.this , "The details has been added", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(CompanyActivity.this, TPOHomeActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(CompanyActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(CompanyActivity.this, "This company already exists", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(CompanyActivity.this, TPOHomeActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
