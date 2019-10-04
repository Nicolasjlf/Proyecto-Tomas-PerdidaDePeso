package com.example.proyectotomasseguimientodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button botonLogin;
    private Button botonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        botonLogin = findViewById(R.id.activityLogin_buttonLogin);
        botonRegister = findViewById(R.id.activityLogin_buttonRegister);
    }
}
