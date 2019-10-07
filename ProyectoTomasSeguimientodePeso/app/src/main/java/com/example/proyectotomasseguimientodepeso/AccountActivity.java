package com.example.proyectotomasseguimientodepeso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {

    private Button botonEditarInformacion;
    private TextView textViewMailUser;
    private TextView textViewNameUser;
    private TextView textViewHeightUser;
    private TextView textViewWeightUser;
    private TextView textViewGoalWeightUser;
    private TextView textViewStepsGoalUser;

    private FirebaseDatabase firebaseDatabase;

    private String unidadPeso;
    private String unidadLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //encuenta todos los componentes visiules
        botonEditarInformacion = findViewById(R.id.activityAccount_EditInfo);
        textViewMailUser = findViewById(R.id.activityAccount_mailUser);
        textViewNameUser = findViewById(R.id.activityAccount_nombreUser);
        textViewHeightUser = findViewById(R.id.activityAccount_AlturaUser);
        textViewWeightUser = findViewById(R.id.activityAccount_pesoActualUser);
        textViewGoalWeightUser = findViewById(R.id.activityAccount_pesoObjetivoUser);
        textViewStepsGoalUser = findViewById(R.id.activityAccount_pasosObjetivoUser);

        // le setea el onclick al boton
        botonEditarInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        //encuentra la base de datos
        firebaseDatabase = FirebaseDatabase.getInstance();
        //agarra la dreferencia de nuesto usuario
        DatabaseReference nameReference = firebaseDatabase.getReference(uid);

        nameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //parsea el json a algo de tipo usuario y lo carga en los componentees visuales
                Usuario value = dataSnapshot.getValue(Usuario.class);
                String valueNombre = value.getNombre();
                Double valueAltura = value.getAltura();
                Double valuePeso = value.getPeso();
                Double valuePesoObjetivo = value.getPesoObjetivo();
                Integer valuePasosObjetivo = value.getPasosObjetivo();

                textViewMailUser.setText("Mail: " + currentUser.getEmail());

                if (value == null) {
                    unidadPeso = " [kg]";
                    unidadLongitud = " [m]";
                } else {

                    if (value.getEnKilos()) {
                        unidadPeso = " [kg]";
                        unidadLongitud = " [m]";
                    } else {
                        unidadPeso = " [lb]";
                        unidadLongitud = " [ft]";
                    }

                    if (valueNombre != null) {
                        textViewNameUser.setText("Name: " + valueNombre);
                    }
                    if (valueAltura != null) {
                        textViewHeightUser.setText("Height: " + valueAltura + unidadLongitud);
                    }
                    if (valuePeso != null) {
                        textViewWeightUser.setText("Weight: " + valuePeso + unidadPeso);
                    }
                    if (valuePesoObjetivo != null) {
                        textViewGoalWeightUser.setText("Weight goal: " + valuePesoObjetivo + unidadPeso);
                    }
                    if (valuePasosObjetivo != null) {
                        textViewStepsGoalUser.setText("Step Goal: " + valuePasosObjetivo);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AccountActivity.this,MainActivity.class));
    }
}
