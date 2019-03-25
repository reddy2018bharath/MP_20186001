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

public class TPOLoginActivity extends AppCompatActivity {
    private EditText inputName, inputId, inputPassword;
    private Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpologin);

        inputName = (EditText) findViewById(R.id.editText3);
        inputId = (EditText) findViewById(R.id.editText4);
        inputPassword = (EditText) findViewById(R.id.editText5);
        btnsignup = (Button) findViewById(R.id.button3);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputName.getText().toString();
                String id = inputId.getText().toString();
                String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter the name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(id)) {
                    Toast.makeText(getApplicationContext(), "Enter the id!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter the password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    ValidateId(username, id, password);
                }
            }

            private void ValidateId(final String username, final String id, final String password) {
                final DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.child("TPO").child(id).exists())) {
                            HashMap<String, Object> tpodataMap = new HashMap<>();
                            tpodataMap.put("Id", id);
                            tpodataMap.put("Username", username);
                            tpodataMap.put("Password", password);

                            mDatabase.child("TPO").child(id).updateChildren(tpodataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(TPOLoginActivity.this , "Congratulations!Your account is created", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(TPOLoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(TPOLoginActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(TPOLoginActivity.this, "This id already exists", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(TPOLoginActivity.this, AdminActivity.class);
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
