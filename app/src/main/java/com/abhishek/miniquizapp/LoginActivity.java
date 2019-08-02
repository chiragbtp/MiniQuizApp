package com.abhishek.miniquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    Button button;
    int counter = 5;
    private TextView user_registration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        username = findViewById(R.id.editText_name);
        password = findViewById(R.id.editText_password);

        button = findViewById(R.id.button_login);
        user_registration = findViewById(R.id.textView_register);


        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null) {
//            finish();
//               startActivity(new Intent(LoginActivity.this, StartActivity.class));
//        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = username.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                if (TextUtils.isEmpty(email1) || TextUtils.isEmpty(password1)) {
                    Toast.makeText(LoginActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password1.length() > 8) {
                    Toast.makeText(LoginActivity.this, "Password too long", Toast.LENGTH_SHORT).show();
                    return;
                }
                validate(username.getText().toString(), password.getText().toString());
            }
        });
        user_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
    }


        public void validate(String username, String password){


                progressDialog.setMessage("Be Ready For The Quiz Until You Are Verified");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(),StartActivity.class));

                                } else {
                                    Toast.makeText(LoginActivity.this,"Login failed or User does not exist",Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                                // ...
                            }
                        });

            }


    }




