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

public class SelectedStudentsActivity extends AppCompatActivity {
    private EditText inputName, inputId, inputCompany;
    private Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_students);

        inputName = (EditText) findViewById(R.id.studentName);
        inputId = (EditText) findViewById(R.id.studentId);
        inputCompany = (EditText) findViewById(R.id.companyPlaced);
        btnsubmit = (Button) findViewById(R.id.button11);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String id = inputId.getText().toString();
                String company = inputCompany.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter the name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(id)) {
                    Toast.makeText(getApplicationContext(), "Enter the id!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(company)) {
                    Toast.makeText(getApplicationContext(), "Enter the company placed!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    ValidateId(name, id, company);
                }

            }
            private void ValidateId(final String name, final String id, final String company) {
                final DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int cnt = 0, cnt1 = 0;
                        for (DataSnapshot data : dataSnapshot.child("Student").getChildren()) {
                            if (id.equals(data.child("Id").getValue().toString()) && name.equals(data.child("Username").getValue().toString())) {
                                cnt = cnt+1;
                            }
                        }
                        for (DataSnapshot data1 : dataSnapshot.child("Company").getChildren()) {
                            if (company.equals(data1.child("Company Name").getValue().toString())) {
                                cnt1 = cnt1 + 1;
                            }
                        }
                        if (cnt == 0) {
                            Toast.makeText(SelectedStudentsActivity.this, "Enter valid Student Details", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (cnt1 == 0) {
                            Toast.makeText(SelectedStudentsActivity.this, "Enter valid Company Name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!(dataSnapshot.child("Selected Students").child(id).exists())) {
                            HashMap<String, Object> selectedMap = new HashMap<>();
                            selectedMap.put("Id", id);
                            selectedMap.put("Name", name);
                            selectedMap.put("Company Placed", company);

                            mDatabase.child("Selected Students").child(id).updateChildren(selectedMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SelectedStudentsActivity.this , "Submitted Successfully", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(SelectedStudentsActivity.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(SelectedStudentsActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SelectedStudentsActivity.this, "This id already exists", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(SelectedStudentsActivity.this, TPOHomeActivity.class);
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
