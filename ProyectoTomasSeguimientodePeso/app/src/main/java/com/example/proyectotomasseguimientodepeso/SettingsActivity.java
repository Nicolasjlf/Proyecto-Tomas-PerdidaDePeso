package com.example.proyectotomasseguimientodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private Switch seleccionSistemaMetricoSwitch;
    private TextView sistemaIntenacionalTextView;
    private TextView sistemaImperialTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seleccionSistemaMetricoSwitch = findViewById(R.id.activitySettings_SwitchSistemaMetrico);
        sistemaIntenacionalTextView = findViewById(R.id.activitySettings_SistemaInternacional_TextView);
        sistemaImperialTextView = findViewById(R.id.activitySettings_SistemaImperial_TextView);

        //TODO: Agarrar el booleano de si está en Sistema Internacional y hacer el setChecked() a la opción correspondiente del Switch

        seleccionSistemaMetricoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    seleccionarSistemaImperial();
                } else {
                    seleccionarSistemaInternacional();
                }
            }
        });
    }

    private void seleccionarSistemaInternacional() {
        marcarSeleccion(sistemaIntenacionalTextView);
        quitarSeleccion(sistemaImperialTextView);
    }

    private void seleccionarSistemaImperial() {
        marcarSeleccion(sistemaImperialTextView);
        quitarSeleccion(sistemaIntenacionalTextView);
    }

    private void marcarSeleccion(TextView textViewSeleccionado) {
        textViewSeleccionado.setBackgroundColor(Color.GRAY);
        textViewSeleccionado.setTextColor(Color.WHITE);
        textViewSeleccionado.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void quitarSeleccion(TextView textViewSeleccionado) {
        textViewSeleccionado.setBackgroundColor(Color.TRANSPARENT);
        textViewSeleccionado.setTextColor(Color.GRAY);
        textViewSeleccionado.setTypeface(Typeface.DEFAULT);
    }
}
