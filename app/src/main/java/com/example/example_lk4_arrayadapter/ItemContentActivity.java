package com.example.example_lk4_arrayadapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.example_lk4_arrayadapter.models.DBHelper;
import com.example.example_lk4_arrayadapter.models.Person;

public class ItemContentActivity extends AppCompatActivity {

    TextView nameView, emailView, indexView;
    ImageView imageView;
    ImageButton btnSave, btnDelete, btnLoadImage;
    String imagePath = "";
    DBHelper dbHelper;

    int id=0;
    private final int GALLERY_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_content);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        indexView = findViewById(R.id.index);
        nameView = findViewById(R.id.name);
        emailView = findViewById(R.id.email);
        imageView = findViewById(R.id.vImage);

        dbHelper = new DBHelper(this);
        Person person = (Person) getIntent().getSerializableExtra("key");
        id=person.id;
        indexView.setText("ID:"+person.id);
        nameView.setText(person.name);
        emailView.setText(person.email);

        try {
            imagePath= person.image;
            loadMedia(imageView);
//            Glide.with(getApplicationContext())
//                    .load(Uri.parse(person.image))
//                    .placeholder(R.drawable.cat_icon)
//                    .error(R.drawable.cat)
//                    .into(imageView);
        } catch (Exception e) {
                            requestPermission();

        }
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);
        btnLoadImage = findViewById(R.id.btnImage);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameTXT = nameView.getText().toString();
                String emailTXT = emailView.getText().toString();
                try {

                        Boolean result = dbHelper.updateUsers(
                                id+"",
                                nameTXT,
                                emailTXT,
                                imagePath
                        );
                        if (result) {
                            Toast.makeText(getApplicationContext(),
                                    "Data updated",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else Toast.makeText(getApplicationContext(),
                                "Cannot update data",
                                Toast.LENGTH_SHORT).show();
                  

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Boolean checkInsert = dbHelper.deleteUsers(
                            id+""
                    );
                    if (checkInsert == true) {
                        Toast.makeText(getApplicationContext(),
                                "Data deleted",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(),
                                "Data not deleted",
                                Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //image load
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                //for gallery
                imagePath = data.getData().toString();
                imageView.setImageURI(data.getData());
//                Glide.with(imageView.getContext())
//                        .load(data.getData())
//                        .into(imageView);
            }
        }
    }
    private static final int PERMISSION_REQUEST_CODE = 100;

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.READ_MEDIA_IMAGES
            }, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with accessing media
                loadMedia(imageView);
            } else {
                // Permissions denied, inform the user
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadMedia(ImageView imageView) {
        imageView.setImageURI(Uri.parse(imagePath));
    }
}