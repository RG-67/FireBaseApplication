package com.example.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edText, edPass;
    private Button loginBtn;
    private TextView loginView;
    private ProgressBar loginBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edText = findViewById(R.id.edText);
        edPass = findViewById(R.id.edPass);
        loginBtn = findViewById(R.id.loginBtn);
        loginView = findViewById(R.id.loginView);
        loginBar = findViewById(R.id.loginBar);
        mAuth = FirebaseAuth.getInstance();

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBar.setVisibility(View.VISIBLE);

                String userText = edText.getText().toString();
                String userPassword = edPass.getText().toString();
                
                if (TextUtils.isEmpty(userText) && TextUtils.isEmpty(userPassword)){
                    Toast.makeText(LoginActivity.this, "Enter your credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(userText, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            loginBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            loginBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Enter valid credentials or check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    protected void onStart() {

        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null){
            Intent intent =new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}