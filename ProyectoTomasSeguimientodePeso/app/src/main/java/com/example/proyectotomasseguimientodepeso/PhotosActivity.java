package com.example.proyectotomasseguimientodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class PhotosActivity extends AppCompatActivity {

    private ImageView imageView1;
    private TextView textView1;
    private ImageView imageView2;
    private TextView textView2;
    private ImageView imageView3;
    private TextView textView3;
    private ImageView imageView4;
    private TextView textView4;
    private ImageView imageView5;
    private TextView textView5;
    private ImageView imageView6;
    private TextView textView6;

    private ImageButton botonSacarFoto;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        imageView1 = findViewById(R.id.activityPhotos_ImageView1);
        textView1 = findViewById(R.id.activityPhotos_TextView1);
        imageView2 = findViewById(R.id.activityPhotos_ImageView2);
        textView2 = findViewById(R.id.activityPhotos_TextView2);
        imageView3 = findViewById(R.id.activityPhotos_ImageView3);
        textView3 = findViewById(R.id.activityPhotos_TextView3);
        imageView4 = findViewById(R.id.activityPhotos_ImageView4);
        textView4 = findViewById(R.id.activityPhotos_TextView4);
        imageView5 = findViewById(R.id.activityPhotos_ImageView5);
        textView5 = findViewById(R.id.activityPhotos_TextView5);
        imageView6 = findViewById(R.id.activityPhotos_ImageView6);
        textView6 = findViewById(R.id.activityPhotos_TextView6);

        botonSacarFoto = findViewById(R.id.activityPhotos_SacarFoto_Button);

        botonSacarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tira un intent al perisferico que abre la camara
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                startActivityForResult(cameraIntent, 0);
            }
        });

    }

    //agarra la informasion que vuelve del intent y los carga en el image view
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Bitmap image = (Bitmap) data.getExtras().get("data");

        calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);

        //cuando vuelve el activity result abre el date picker dialog para que el usuario seleccione la fecha
        datePickerDialog = new DatePickerDialog(PhotosActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int unAnio, int unMes, int unDia) {
                setearImagenYFecha(image, unDia, unMes + 1, unAnio);
                //Toast.makeText(PhotosActivity.this, unDia+"/"+unMes+1+"/"+unAnio, Toast.LENGTH_SHORT).show();
            }
        }, anio, mes, dia);

        datePickerDialog.show();
    }

    private void setearImagenYFecha(Bitmap image, int unDia, int unMes, int unAnio) {
        // este codigo agarra el intent de la imagen y depndiendo de cual esta vacia la va poniendo
        if (textView1.getText().equals("")) {
            imageView1.setImageBitmap(image);
            textView1.setText(unDia + "/" + unMes + "/" + unAnio);
            textView1.setBackgroundColor(Color.GRAY);
        } else if (textView2.getText().equals("")) {
            imageView2.setImageBitmap(image);
            textView2.setText(unDia + "/" + unMes + "/" + unAnio);
            textView2.setBackgroundColor(Color.GRAY);
        } else if (textView3.getText().equals("")) {
            imageView3.setImageBitmap(image);
            textView3.setText(unDia + "/" + unMes + "/" + unAnio);
            textView3.setBackgroundColor(Color.GRAY);
        } else if (textView4.getText().equals("")) {
            imageView4.setImageBitmap(image);
            textView4.setText(unDia + "/" + unMes + "/" + unAnio);
            textView4.setBackgroundColor(Color.GRAY);
        } else if (textView5.getText().equals("")) {
            imageView5.setImageBitmap(image);
            textView5.setText(unDia + "/" + unMes + "/" + unAnio);
            textView5.setBackgroundColor(Color.GRAY);
        } else if (textView6.getText().equals("")) {
            imageView6.setImageBitmap(image);
            textView6.setText(unDia + "/" + unMes + "/" + unAnio);
            textView6.setBackgroundColor(Color.GRAY);
        } else {
            Toast.makeText(this, "No more room for photos", Toast.LENGTH_SHORT).show();
        }
    }
}
