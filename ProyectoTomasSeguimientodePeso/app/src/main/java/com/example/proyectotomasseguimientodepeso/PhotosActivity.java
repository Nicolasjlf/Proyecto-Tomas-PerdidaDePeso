package com.example.proyectotomasseguimientodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class PhotosActivity extends AppCompatActivity {

    private ImageView fotoSacada;
    private ImageButton botonSacarFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        fotoSacada = findViewById(R.id.activityPhotos_FotoSacada_ImageView);
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
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap image = (Bitmap) data.getExtras().get("data");
        fotoSacada.setImageBitmap(image);
    }
}
