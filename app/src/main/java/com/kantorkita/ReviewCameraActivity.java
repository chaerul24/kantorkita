package com.kantorkita;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ReviewCameraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && !bundle.getString("image", "").isEmpty()){
            String image = bundle.getString("image", "");
            ImageView imageView = findViewById(R.id.image_review);
            Glide.with(this).load(image).into(imageView);
        }
    }
}
