package com.example.camara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button trigger = findViewById(R.id.trigger);
        trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        Log.i("FUNCIÓ", "Entrant a dispatchTakePictureIntent");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.i("FUNCIÓ", "Entrant a dispatchTakePictureIntent2");
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = getFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                Toast.makeText(this, "Start Activity Camera", Toast.LENGTH_SHORT);
            }
        }
    }

    protected File getFile() throws IOException{
        // Guardar a un fitxer
        File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File foto = new File(path, "imatge.jpg");
        return foto;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final ImageView imageView = findViewById(R.id.imageView);
        try {
            Uri fileUri = Uri.fromFile(getFile());
            imageView.setImageURI(fileUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}