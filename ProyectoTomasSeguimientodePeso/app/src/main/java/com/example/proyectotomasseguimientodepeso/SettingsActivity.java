package com.example.proyectotomasseguimientodepeso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    private TextView unidadPesoTextView;
    private TextView unidadLongitudTextView1;
    private TextView unidadLongitudTextView2;

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
        unidadPesoTextView = findViewById(R.id.activitySettings_UnidadPeso_TextView);
        unidadLongitudTextView1 = findViewById(R.id.activitySettings_UnidadLongitud1_TextView);
        unidadLongitudTextView2 = findViewById(R.id.activitySettings_UnidadLongitud2_TextView);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference(uid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        unidadPesoTextView.setText("[m]");
        unidadLongitudTextView1.setText("[kg]");
        unidadLongitudTextView2.setText("[kg]");

        String alturaEnMetros = alturaEditText.getText().toString();
        if(!alturaEnMetros.equals("")){
            alturaEditText.setText(convertirAPies(alturaEnMetros));
        }

        String pesoEnKilos = pesoEditText.getText().toString();
        if(!pesoEnKilos.equals("")){
            pesoEditText.setText(convertirALibras(pesoEnKilos));
        }

        String pesoObjetivoEnKilos = pesoObjetivoEditText.getText().toString();
        if(!pesoObjetivoEnKilos.equals("")){
            pesoObjetivoEditText.setText(convertirALibras(pesoObjetivoEnKilos));
        }
    }

    private void seleccionarSistemaImperial() {
        marcarSeleccion(sistemaImperialTextView);
        quitarSeleccion(sistemaIntenacionalTextView);

        unidadPesoTextView.setText("[ft]");
        unidadLongitudTextView1.setText("[lb]");
        unidadLongitudTextView2.setText("[lb]");

        String alturaEnPies = alturaEditText.getText().toString();
        if(!alturaEnPies.equals("")){
            alturaEditText.setText(convertirAMetros(alturaEnPies));
        }

        String pesoEnLibras = pesoEditText.getText().toString();
        if(!pesoEnLibras.equals("")){
            pesoEditText.setText(convertirAKilos(pesoEnLibras));
        }

        String pesoObjetivoEnLibras = pesoObjetivoEditText.getText().toString();
        if(!pesoObjetivoEnLibras.equals("")){
            pesoObjetivoEditText.setText(convertirAKilos(pesoObjetivoEnLibras));
        }
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

    private String convertirAPies(String metros){
        Double pies =  Double.parseDouble(metros) * 3.281;
        return String.format("%.2f",pies);
    }

    private String convertirAMetros(String pies){
        Double metros =  Double.parseDouble(pies) / 3.281;
        return String.format("%.2f",metros);
    }

    private String convertirALibras(String kilos){
        Double libras =  Double.parseDouble(kilos) * 2.205;
        return String.format("%.2f",libras);
    }

    private String convertirAKilos(String libras){
        Double kilos =  Double.parseDouble(libras) / 2.205;
        return String.format("%.2f",kilos);

    }
}
