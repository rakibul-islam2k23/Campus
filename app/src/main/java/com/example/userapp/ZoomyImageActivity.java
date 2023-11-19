package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

public class ZoomyImageActivity extends AppCompatActivity {
    TouchImageView touchImageView;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomy_image);
        touchImageView = findViewById(R.id.touchImageView);
        back = findViewById(R.id.backImageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        String url = bundle.getString("url");
        Picasso.get().load(url).into(touchImageView);


    }
}