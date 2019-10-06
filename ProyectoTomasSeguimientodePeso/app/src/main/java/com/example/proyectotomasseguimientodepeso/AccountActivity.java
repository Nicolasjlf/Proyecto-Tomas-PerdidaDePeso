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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        botonEditarInformacion = findViewById(R.id.activityAccount_EditInfo);
        textViewMailUser = findViewById(R.id.activityAccount_mailUser);
        textViewNameUser = findViewById(R.id.activityAccount_nombreUser);
        textViewHeightUser = findViewById(R.id.activityAccount_AlturaUser);
        textViewWeightUser = findViewById(R.id.activityAccount_pesoActualUser);
        textViewGoalWeightUser = findViewById(R.id.activityAccount_pesoObjetivoUser);
        textViewStepsGoalUser = findViewById(R.id.activityAccount_pasosObjetivoUser);

        botonEditarInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference nameReference = firebaseDatabase.getReference(uid);

        nameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario value = dataSnapshot.getValue(Usuario.class);

                CharSequence mail = textViewMailUser.getText();
                textViewMailUser.setText(mail + currentUser.getEmail());
                CharSequence name = textViewNameUser.getText();
                textViewNameUser.setText(name + value.getNombre());
                CharSequence height = textViewHeightUser.getText();
                textViewHeightUser.setText(height + value.getAltura().toString());
                CharSequence weight = textViewWeightUser.getText();
                textViewWeightUser.setText(weight + value.getPeso().toString());
                CharSequence goalWeight = textViewGoalWeightUser.getText();
                textViewGoalWeightUser.setText(goalWeight + value.getPesoObjetivo().toString());
                CharSequence stepsGoal = textViewStepsGoalUser.getText();
                textViewStepsGoalUser.setText(stepsGoal + value.getPasosObjetivo().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
