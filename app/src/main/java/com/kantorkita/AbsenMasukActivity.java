package com.kantorkita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AbsenMasukActivity extends AppCompatActivity {
    private ImageView back;
    private Button btnCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi_masuk);

        back = findViewById(R.id.back);
        back.setOnClickListener(v-> finish());

        btnCamera = findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(v-> {
            Intent intent = new Intent(AbsenMasukActivity.this, CameraActivtiy.class);
            startActivity(intent);
        });
        
    }
}
