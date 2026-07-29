package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LogIn extends AppCompatActivity {
    TextView txtForgot;
    EditText etUsername,etPassword;
    Button btnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername=findViewById(R.id.etUsername);
        txtForgot=findViewById(R.id.txtForgot);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    etUsername.setError("Enter Username");
                    return;
                }
                if (password.isEmpty()) {
                    etPassword.setError("Enter password");
                    return;
                }
                if (username.equals("admin") && password.equals("AdminQ")) {

                    Intent intent = new Intent(LogIn.this, Dashboard.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LogIn.this,
                            "Wrong Username or Password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}






