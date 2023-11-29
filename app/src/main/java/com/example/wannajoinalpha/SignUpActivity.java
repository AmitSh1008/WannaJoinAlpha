package com.example.wannajoinalpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText etUsername, etEmail, etPassword, etConPassword, etPhoneNum;
    private Button btnSignup;
    private TextView tvLoginRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.editTextUserName);
        etEmail = findViewById(R.id.editTextNewEmail);
        etPassword = findViewById(R.id.editTextNewPassword);
        etConPassword = findViewById(R.id.editTextConPassword);
        etPhoneNum = findViewById(R.id.editTextPhoneNumber);
        btnSignup = findViewById(R.id.buttonSignUp);
        tvLoginRedirect = findViewById(R.id.textViewLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String conPassword = etConPassword.getText().toString().trim();
                String phoneNum = etPhoneNum.getText().toString().trim();

                boolean allValid = true;
                if (username.isEmpty())
                {
                    etUsername.setError("username cannot be empty!");
                    allValid = false;
                }
                if (email.isEmpty())
                {
                    etEmail.setError("email cannot be empty!");
                    allValid = false;
                }
                if (password.isEmpty())
                {
                    etPassword.setError("password cannot be empty!");
                    allValid = false;
                }
                if (conPassword.isEmpty())
                {
                    etConPassword.setError("confirm password cannot be empty!");
                    allValid = false;
                }
                if (phoneNum.isEmpty())
                {
                    etPhoneNum.setError("phone number cannot be empty!");
                    allValid = false;
                }
                if (allValid)
                {
                    if (password.equals(conPassword))
                    {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(SignUpActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this, "Signup Failed " + task.getException().getMessage() + '!', Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        etConPassword.setError("password does not match!");
                    }
                }
            }
        });

        tvLoginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });
    }
}