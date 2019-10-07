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
    private Boolean sistemaInternacional;

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

                //traer el usuario de firebase
                usuario = dataSnapshot.getValue(Usuario.class);
                String usuarioNombre = usuario.getNombre();
                Double usuarioAltura = usuario.getAltura();
                Double usuarioPeso = usuario.getPeso();
                Double usuarioPesoObjetivo = usuario.getPesoObjetivo();
                Integer usuarioPasosObjetivo = usuario.getPasosObjetivo();

                //si el usuario no es null carga todos los atributos en los campos correspondientes
                if (usuario == null) {
                    seleccionSistemaMetricoSwitch.setChecked(false);
                    seleccionarSistemaInternacional();
                    usuario = new Usuario();
                } else {

                    if (usuario.getEnKilos() == null || usuario.getEnKilos() == true) {
                        seleccionSistemaMetricoSwitch.setChecked(false);
                        seleccionarSistemaInternacional();
                    } else {
                        seleccionSistemaMetricoSwitch.setChecked(true);
                        seleccionarSistemaImperial();
                    }

                    if (usuarioNombre != null) {
                        nombreEditText.setText(usuarioNombre);
                    }
                    if (usuarioAltura != null) {
                        alturaEditText.setText(usuarioAltura.toString());
                    }
                    if (usuarioPeso != null) {
                        pesoEditText.setText(usuarioPeso.toString());
                    }
                    if (usuarioPesoObjetivo != null) {
                        pesoObjetivoEditText.setText(usuarioPesoObjetivo.toString());
                    }
                    if (usuarioPasosObjetivo != null) {
                        pasosObjetivoEditText.setText(usuarioPasosObjetivo.toString());
                    }

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

    //este metodo carga el usuario en la base de dato de firebase, agarrando el valor de cada campo
    private void setUsuarioFirebase(DatabaseReference databaseReference) {
        String nombreUsuario = nombreEditText.getText().toString();
        this.usuario.setNombre(nombreUsuario);
        if (!alturaEditText.getText().toString().equals("")) {
            String alturaUsuario = alturaEditText.getText().toString();
            this.usuario.setAltura(Double.parseDouble(alturaUsuario));
        }
        if (!pesoEditText.getText().toString().equals("")) {
            String pesoUsuario = pesoEditText.getText().toString();
            this.usuario.setPeso(Double.parseDouble(pesoUsuario));
        }
        if (!pesoObjetivoEditText.getText().toString().equals("")) {
            String pesoObjetivoUsuario = pesoObjetivoEditText.getText().toString();
            this.usuario.setPesoObjetivo(Double.parseDouble(pesoObjetivoUsuario));
        }
        if (!pasosObjetivoEditText.getText().toString().equals("")) {
            String pasosObjetivoUsuario = pasosObjetivoEditText.getText().toString();
            this.usuario.setPasosObjetivo(Integer.parseInt(pasosObjetivoUsuario));
        }

        this.usuario.setEnKilos(sistemaInternacional);

        databaseReference.setValue(this.usuario);
    }

    //este metodo cambia el sistema en el que se ve modificando todos los aspectos visuales de manera acorde
    private void seleccionarSistemaInternacional() {

        sistemaInternacional = true;

        marcarSeleccion(sistemaIntenacionalTextView);
        quitarSeleccion(sistemaImperialTextView);

        unidadPesoTextView.setText("[m]");
        unidadLongitudTextView1.setText("[kg]");
        unidadLongitudTextView2.setText("[kg]");

        String alturaEnMetros = alturaEditText.getText().toString();
        if (!alturaEnMetros.equals("")) {
            alturaEditText.setText(convertirAPies(alturaEnMetros));
        }

        String pesoEnKilos = pesoEditText.getText().toString();
        if (!pesoEnKilos.equals("")) {
            pesoEditText.setText(convertirALibras(pesoEnKilos));
        }

        String pesoObjetivoEnKilos = pesoObjetivoEditText.getText().toString();
        if (!pesoObjetivoEnKilos.equals("")) {
            pesoObjetivoEditText.setText(convertirALibras(pesoObjetivoEnKilos));
        }
    }

    private void seleccionarSistemaImperial() {

        sistemaInternacional = false;

        marcarSeleccion(sistemaImperialTextView);
        quitarSeleccion(sistemaIntenacionalTextView);

        unidadPesoTextView.setText("[ft]");
        unidadLongitudTextView1.setText("[lb]");
        unidadLongitudTextView2.setText("[lb]");

        String alturaEnPies = alturaEditText.getText().toString();
        if (!alturaEnPies.equals("")) {
            alturaEditText.setText(convertirAMetros(alturaEnPies));
        }

        String pesoEnLibras = pesoEditText.getText().toString();
        if (!pesoEnLibras.equals("")) {
            pesoEditText.setText(convertirAKilos(pesoEnLibras));
        }

        String pesoObjetivoEnLibras = pesoObjetivoEditText.getText().toString();
        if (!pesoObjetivoEnLibras.equals("")) {
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

    //metodos de conversion para pasar de un sistema a otro
    private String convertirAPies(String metros) {
        Double pies = Double.parseDouble(metros) * 3.281;
        return String.format("%.2f", pies);
    }

    private String convertirAMetros(String pies) {
        Double metros = Double.parseDouble(pies) / 3.281;
        return String.format("%.2f", metros);
    }

    private String convertirALibras(String kilos) {
        Double libras = Double.parseDouble(kilos) * 2.205;
        return String.format("%.2f", libras);
    }

    private String convertirAKilos(String libras) {
        Double kilos = Double.parseDouble(libras) / 2.205;
        return String.format("%.2f", kilos);

    }
}
