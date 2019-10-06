package com.example.proyectotomasseguimientodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

    private Switch seleccionSistemaMetricoSwitch;
    private TextView sistemaIntenacionalTextView;
    private TextView sistemaImperialTextView;
    private FirebaseDatabase firebaseDatabase;
    private Button confirmChangesButton;
    private EditText nombreEditText;
    private EditText alturaEditText;
    private EditText pesoEditText;
    private EditText pesoObjetivoEditText;
    private EditText pasosObjetivoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seleccionSistemaMetricoSwitch = findViewById(R.id.activitySettings_SwitchSistemaMetrico);
        sistemaIntenacionalTextView = findViewById(R.id.activitySettings_SistemaInternacional_TextView);
        sistemaImperialTextView = findViewById(R.id.activitySettings_SistemaImperial_TextView);
        nombreEditText = findViewById(R.id.activitySettings_Name_EditText);
        alturaEditText = findViewById(R.id.activitySettings_Height_EditText);
        pesoEditText = findViewById(R.id.activitySettings_Weight_EditText);
        pesoObjetivoEditText = findViewById(R.id.activitySettings_GoalWeight_EditText);
        pasosObjetivoEditText = findViewById(R.id.activitySettings_StepGoal_EditText);
        confirmChangesButton = findViewById(R.id.activitySettings_Confirmar_Button);

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

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(uid);

        confirmChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("nombre").setValue(nombreEditText.getText().toString());
                //TODO: Pasar esto para que escriba un objeto Usuario (POJO), no dato por dato.
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
