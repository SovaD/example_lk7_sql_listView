package com.example.example_lk4_arrayadapter.models;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.example_lk4_arrayadapter.R;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Person> {
    public MyAdapter(@NonNull Context context, List<Person> resource) {
        super(context, R.layout.activity_item, resource);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, null);
        final Person person = getItem(position);

        ((TextView) convertView.findViewById(R.id.name)).setText(person.name);

        ImageView imageView = convertView.findViewById(R.id.image);
        // imageView.setImageURI(Uri.parse(person.image));

        try {
            Glide.with(imageView.getContext())
                    .load(Uri.parse(person.image))
                    .placeholder(R.drawable.cat_icon)
                    .error(R.drawable.cat)
                    .circleCrop()
                    .into(imageView);
        } catch (Exception e) {
        }
        return convertView;
    }

}
