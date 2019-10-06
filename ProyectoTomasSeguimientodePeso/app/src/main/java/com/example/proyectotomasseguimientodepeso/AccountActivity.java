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

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        botonEditarInformacion = findViewById(R.id.activityAccount_EditInfo);
        textViewMailUser = findViewById(R.id.activityAccount_mailUser);
        textViewNameUser = findViewById(R.id.activityAccount_nombreUser);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        CharSequence textoPrevio = textViewMailUser.getText();
        textViewMailUser.setText(textoPrevio + email);

        botonEditarInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        String uid = currentUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference nameReference = firebaseDatabase.getReference(uid).child("nombre");
        //TODO: Leer la referencia del POJO, no dato por dato

        nameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                CharSequence text = textViewNameUser.getText();
                textViewNameUser.setText(text+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
