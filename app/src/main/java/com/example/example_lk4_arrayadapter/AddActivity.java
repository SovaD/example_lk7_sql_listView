package com.example.example_lk4_arrayadapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.example_lk4_arrayadapter.models.DBHelper;
import com.example.example_lk4_arrayadapter.models.Person;

public class AddActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1;
    EditText tName, tEmail;
    Uri imagePath = null;
    ImageButton bImage;
    ImageView vImage;
    Button bSave;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tName = findViewById(R.id.tName);
        tEmail = findViewById(R.id.tEmail);
        bImage = findViewById(R.id.btnImage);
        vImage = findViewById(R.id.vImage);
        bSave = findViewById(R.id.btnSave);

        dbHelper = new DBHelper(this);
        bImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //image load
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = tName.getText().toString();
                String emailTXT = tEmail.getText().toString();
                if (nameTXT.isEmpty() || emailTXT.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Fill data to add",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String imageTXT = imagePath.toString();
                if (imageTXT.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Load image",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Person person = new Person(nameTXT, emailTXT, imageTXT);

                    Boolean checkInsert = dbHelper.checkData(emailTXT);

                    if (!checkInsert) {
                        Boolean result = dbHelper.insertUser(person);
                        if (result) {
                            Toast.makeText(getApplicationContext(),
                                    "New data inserted",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else Toast.makeText(getApplicationContext(),
                                "Cannot add data",
                                Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(),
                                "Email already exist",
                                Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                //for gallery
                imagePath = data.getData();
                vImage.setImageURI(imagePath);
//                Glide.with(imageView.getContext())
//                        .load(data.getData())
//                        .into(imageView);

            }
        }
    }
}