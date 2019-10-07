package com.example.proyectotomasseguimientodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botonMyAccount;
    private Button botonHistory;
    private Button botonSettings;
    private Button botonMyPics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonMyAccount = findViewById(R.id.mainActivity_myAcount);
        botonHistory = findViewById(R.id.mainActivity_histoy);
        botonSettings= findViewById(R.id.mainActivity_settings);
        botonMyPics= findViewById(R.id.mainActivity_myPics);

        //cada boton tira un intent a las diferentes activities de la aplicacion

        botonMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        botonMyPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PhotosActivity.class);
                startActivity(intent);
            }
        });
        botonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
        botonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);



            }
        });


    }
}
