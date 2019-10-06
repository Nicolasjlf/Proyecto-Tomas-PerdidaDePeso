package com.example.proyectotomasseguimientodepeso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private Usuario usuario;

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

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);

                if (usuario.getEnKilos() == null || usuario.getEnKilos() == true) {
                    seleccionSistemaMetricoSwitch.setChecked(false);
                    seleccionarSistemaInternacional();
                } else {
                    seleccionSistemaMetricoSwitch.setChecked(true);
                    seleccionarSistemaImperial();
                }

                nombreEditText.setText(usuario.getNombre());
                alturaEditText.setText(usuario.getAltura().toString());
                pesoEditText.setText(usuario.getPeso().toString());
                pesoObjetivoEditText.setText(usuario.getPesoObjetivo().toString());
                pasosObjetivoEditText.setText(usuario.getPasosObjetivo().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seleccionSistemaMetricoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    seleccionarSistemaImperial();
                } else {
                    seleccionarSistemaInternacional();
                }

                usuario.setEnKilos(!isChecked);
            }
        });

        confirmChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUsuarioFirebase(databaseReference);

                Toast.makeText(SettingsActivity.this, "Updated information", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SettingsActivity.this, AccountActivity.class));
            }
        });
    }

    private void setUsuarioFirebase(DatabaseReference databaseReference) {
        String nombreUsuario = nombreEditText.getText().toString();
        usuario.setNombre(nombreUsuario);
        String alturaUsuario = alturaEditText.getText().toString();
        usuario.setAltura(Double.parseDouble(alturaUsuario));
        String pesoUsuario = pesoEditText.getText().toString();
        usuario.setPeso(Double.parseDouble(pesoUsuario));
        String pesoObjetivoUsuario = pesoObjetivoEditText.getText().toString();
        usuario.setPesoObjetivo(Double.parseDouble(pesoObjetivoUsuario));
        String pasosObjetivoUsuario = pasosObjetivoEditText.getText().toString();
        usuario.setPasosObjetivo(Integer.parseInt(pasosObjetivoUsuario));

        databaseReference.setValue(usuario);
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
