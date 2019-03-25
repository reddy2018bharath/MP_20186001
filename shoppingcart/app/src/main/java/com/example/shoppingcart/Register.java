package com.example.shoppingcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class Register extends AppCompatActivity {


    private EditText uname, pwd, phoneno;
    private Button reg, backtoLogin;
//    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        phoneno = findViewById(R.id.phoneno);
        reg = findViewById(R.id.register);
        backtoLogin = findViewById(R.id.login);

        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                createAccount();
            }
        });

        backtoLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });
    }
    public void createAccount(){
        String name = uname.getText().toString();
        String password = pwd.getText().toString();
        String phonenumber = phoneno.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "enter user name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "enter password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phonenumber)){
            Toast.makeText(this, "enter phonenumber",Toast.LENGTH_SHORT).show();
        } else {
            checkExistOrNot(name,password,phonenumber);
        }
    }

    public void checkExistOrNot(final String n, final String p, final String pno){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(pno).exists())){
                    HashMap<String, Object> user = new HashMap<>();
                    user.put("phone", pno);
                    user.put("password", p);
                    user.put("username", n);
                    RootRef.child("users").child(pno).updateChildren(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()) {
                                       Toast.makeText(Register.this, "registered successfully", Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(Register.this, Login.class);
                                       startActivity(intent);

                                   }else{
                                       Intent intent = new Intent(Register.this, MainActivity.class);
                                       startActivity(intent);

                                       Toast.makeText(Register.this, "network error please try again", Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
