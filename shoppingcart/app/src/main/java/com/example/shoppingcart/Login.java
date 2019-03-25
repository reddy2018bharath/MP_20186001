package com.example.shoppingcart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.shoppingcart.Model.User;
import com.example.shoppingcart.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {
    private EditText Phone, Password;
    private Button login,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Phone = findViewById(R.id.Phone);
        Password= findViewById(R.id.password);
        login = findViewById(R.id.login);
//        signup = findViewById(R.id.register);



        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loginuser();
            }

        });


    }

    public void loginuser(){
        String phone = Phone.getText().toString();
        String pwd = Password.getText().toString();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "user name should not empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "password should not empty", Toast.LENGTH_SHORT).show();
        } else {
           checkingaccess(phone, pwd);
        }
    }


    public void checkingaccess(final String p, final String pwd){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("users").child(p).exists()){

                    User userData = dataSnapshot.child("users").child(p).getValue(User.class);

                    if(userData.getPhone().equals(p) && userData.getPassword().equals(pwd)){


                        Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(Login.this, productsInformation.class);
                        i.putExtra("phone", p);
                        i.putExtra("pwd",pwd);
                        startActivity(i);
                    } else{

                    }
                } else {
                    Toast.makeText(Login.this, "Enter correct credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
