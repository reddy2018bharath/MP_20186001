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


public class StudentLoginActivity extends AppCompatActivity {
    private EditText inputName, inputId, inputBranch, inputPassword;
    private Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        inputName = (EditText) findViewById(R.id.editText);
        inputId = (EditText) findViewById(R.id.editText2);
        inputBranch = (EditText) findViewById(R.id.editText6);
        inputPassword = (EditText) findViewById(R.id.editText7);
        btnsignup = (Button) findViewById(R.id.button4);


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputName.getText().toString().trim();
                String id = inputId.getText().toString().trim();
                String branch = inputBranch.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter the name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(id)) {
                    Toast.makeText(getApplicationContext(), "Enter the id!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(branch)) {
                    Toast.makeText(getApplicationContext(), "Enter the branch name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter the password!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ValidateId(username, id, branch, password);
                }
            }

            private void ValidateId(final String username, final String id, final String branch, final String password) {
                final DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.child("Student").child(id).exists())) {
                            HashMap<String, Object> studentdataMap = new HashMap<>();
                            studentdataMap.put("Id", id);
                            studentdataMap.put("Username", username);
                            studentdataMap.put("Branch", branch);
                            studentdataMap.put("Password", password);

                            mDatabase.child("Student").child(id).updateChildren(studentdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(StudentLoginActivity.this, "Congratulations!Your account is created", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(StudentLoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(StudentLoginActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(StudentLoginActivity.this, "This id already exists", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(StudentLoginActivity.this, AdminActivity.class);
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
