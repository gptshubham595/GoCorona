package com.example.gocorona.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gocorona.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPass extends AppCompatActivity {

    Button forgot;
    EditText forgotpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        forgot = findViewById(R.id.forgotbtn);
        forgotpass = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgotpass.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Check Your mail and reset your password", Toast.LENGTH_LONG).show();
                                Intent a = new Intent(getApplicationContext(), LoginScreen.class);
                                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(a);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a email..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
