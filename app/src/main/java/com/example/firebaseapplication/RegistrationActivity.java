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

public class RegistrationActivity extends AppCompatActivity {

    private EditText editName, editPassword, editRePassword;
    private Button registerBtn;
    private TextView registerView;
    private ProgressBar registerPBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);
        editRePassword = findViewById(R.id.editRePassword);
        registerBtn = findViewById(R.id.registerBtn);
        registerView = findViewById(R.id.registerView);
        registerPBar = findViewById(R.id.registerPBar);
        mAuth = FirebaseAuth.getInstance();

        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPBar.setVisibility(view.VISIBLE);

                String userName = editName.getText().toString();
                String userPass = editPassword.getText().toString();
                String userRePass = editRePassword.getText().toString();

                if (!userPass.equals(userRePass)){
                    Toast.makeText(RegistrationActivity.this, "Check your both password same or not", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(userPass) && TextUtils.isEmpty(userRePass)){
                    Toast.makeText(RegistrationActivity.this, "Check your credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(userName, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                registerPBar.setVisibility(view.GONE);
                                Toast.makeText(RegistrationActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}