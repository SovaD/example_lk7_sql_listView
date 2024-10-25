package com.example.example_lk4_arrayadapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.example_lk4_arrayadapter.models.DBHelper;
import com.example.example_lk4_arrayadapter.models.MyAdapter;
import com.example.example_lk4_arrayadapter.models.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper = new DBHelper(this);
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fillData();
        listView = findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(this, dbHelper.getListData());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Person person = (Person) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), ItemContentActivity.class);
                intent.putExtra("key", person);
                startActivity(intent);

            }
        });

        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

        MyAdapter adapter = new MyAdapter(this, dbHelper.getListData());
        listView.setAdapter(adapter);
    }

        void fillData(){
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Tom","tom@gmail.com","R.drawable.cat"),
                new Person("Sam","samm@gmail.com","R.drawable.dog"),
                new Person("Alice","alice@gmail.com","R.drawable.cat_icon"),
                new Person("Kate","kate@gmail.com","R.drawable.dog"),
                new Person("Peter","peter@gmail.com","R.drawable.cat"),
                new Person("Henry","henry@gmail.com","R.drawable.cat_icon"),
                new Person("Lee","lee@gmail.com","R.drawable.cat"),
                new Person("Anne","anne@gmail.com","R.drawable.dog"),
                new Person("Oleg","oleg@gmail.com","R.drawable.cat_icon"),
                new Person("Ken","ken@gmail.com","R.drawable.cat")
        ));

        people.forEach(x->{if(!dbHelper.checkData(x.email)) dbHelper.insertUser(x);});
    }
}