package com.example.proyectotomasseguimientodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {


    TextView textViewHoraActual;
    Button botonGuardarCambios;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textViewHoraActual = findViewById(R.id.historyActivity_currentTime);
        progressBar = findViewById(R.id.history_progressBar);
        botonGuardarCambios = findViewById(R.id.historyActivity_botonGuardar);

        String dateStr = "04/05/2010";

        //este pedazo de codigo busca la hora actual y la muestra en el text view
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");

        String newDateStr = postFormater.format(dateObj);

        textViewHoraActual.setText(newDateStr);

        //como no hay que guardar los datos este metodo es puro show pone un progres var 2 segundos y los saca
        botonGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(HistoryActivity.this, "your progress was saved successfully", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);   //5 seconds
            }
        });
    }
}
