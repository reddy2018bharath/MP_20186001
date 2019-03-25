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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentActivity extends AppCompatActivity {
    private EditText inputId, inputPassword;
    private Button btnSignin;
    private String parentDbName = "Student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        inputId = (EditText) findViewById(R.id.editText11);
        inputPassword = (EditText) findViewById(R.id.editText12);
        btnSignin = (Button) findViewById(R.id.button6);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = inputId.getText().toString();
                String password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(id)) {
                    Toast.makeText(getApplicationContext(), "Enter valid Id!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    AllowAccessToAccount(id, password);
                }
            }
            private void AllowAccessToAccount(final String id, final String password) {
                final DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(parentDbName).child(id).exists()) {
                            if(dataSnapshot.child(parentDbName).child(id).child("Password").getValue().toString().equals(password)){
                                Toast.makeText(StudentActivity.this, "logged in Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(StudentActivity.this, StudentHomeActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(StudentActivity.this, "Enter correct password!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Account does not exist", Toast.LENGTH_SHORT).show();
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
