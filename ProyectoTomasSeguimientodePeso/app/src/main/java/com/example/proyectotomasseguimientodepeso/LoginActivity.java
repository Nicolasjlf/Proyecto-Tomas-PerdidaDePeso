package com.example.proyectotomasseguimientodepeso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    //cada actividad va tenes como atributo los componentes visuales
    private Button botonLogin;
    private Button botonRegister;

    private EditText editTextEmail;
    private EditText editTextpass;

    //el firebase auth es basicamente el componente que maneja el login de firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //encuentra todos los componentes visiuales de la pantalla usando el find view by id
        botonLogin = findViewById(R.id.activityLogin_buttonLogin);
        botonRegister = findViewById(R.id.activityLogin_buttonRegister);
        editTextEmail = findViewById(R.id.activityLogin_username);
        editTextpass = findViewById(R.id.activityLogin_pasword);

        mAuth = FirebaseAuth.getInstance();


        botonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //encuenta los valores del edit text y los manda al metodo crear usuario
                String email = editTextEmail.getText().toString();
                String password = editTextpass.getText().toString();
                crearUsuario(email,password);
            }
        });
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextpass.getText().toString();
                loguearUsuario(email,password);
            }
        });



    }

    private void crearUsuario(String email, String password){

        //metodo de firebase para crear usuario
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //lo que se ejecuta si el metodo es exitoso
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            Usuario usuario = new Usuario();
                            usuario.setEnKilos(true);
                            usuario.setAltura(0.0);
                            usuario.setNombre("");
                            usuario.setPasosObjetivo(0);
                            usuario.setPeso(0.0);
                            firebaseDatabase.getReference().child(user.getUid()).setValue(usuario);

                        } else {
                            //lo que se ejecuta si el metodo falla
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void loguearUsuario(String email,String password){

        //metodo de firebase para crear usuario
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //si el logueo es exitoso te manda a la main activity
                            irAMain();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void irAMain(){
        //intent para ir al main
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
