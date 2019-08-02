package com.abhishek.miniquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class RegistrationActivity extends AppCompatActivity {
    private EditText username, useremail,userpassword,confirmpass;
    private Button signup;
    TextView userlogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        username = findViewById(R.id.editText_username);
        useremail = findViewById(R.id.editText_email);
        userpassword= findViewById(R.id.editText_password);
        signup = findViewById(R.id.button_signup);
        userlogin = findViewById(R.id.textView_login);
        confirmpass=findViewById(R.id.editText_conf);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = useremail.getText().toString().trim();
                String user = username.getText().toString().trim();
                String pass = userpassword.getText().toString().trim();
                String confpass=confirmpass.getText().toString().trim();


                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)||TextUtils.isEmpty(confpass)) {
                    Toast.makeText(RegistrationActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() > 10) {
                    Toast.makeText(RegistrationActivity.this, "Password too long", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.equals(confpass)){
                    progressDialog.setMessage("Be Ready For The Quiz Until You Are Verified");
                    progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                    progressDialog.show();


                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();

                                        startActivity(new Intent(getApplicationContext(), StartActivity.class));
                                        Toast.makeText(RegistrationActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegistrationActivity.this, "Registraion failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                else{
                    Toast.makeText(RegistrationActivity.this,"Password And Confirm password mismatch",Toast.LENGTH_SHORT).show();
                }
                userlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }
                });
            }
        });
               }
}

