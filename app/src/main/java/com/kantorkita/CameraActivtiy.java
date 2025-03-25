package com.kantorkita;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraActivtiy  extends AppCompatActivity {
    private PreviewView cameraView;
    private ImageView takePhoto, sensorFace, switchCamera;
    private ImageCapture imageCapture;
    private CameraSelector cameraSelector;
    private Camera camera;
    private ProcessCameraProvider cameraProvider;
    private ExecutorService cameraExecutor;
    private boolean isFrontCamera = false; // Untuk switch kamera

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraView = findViewById(R.id.previewView);
        takePhoto = findViewById(R.id.btn_take);
        sensorFace = findViewById(R.id.btn_sensor_face);
        switchCamera = findViewById(R.id.btn_switch);

        cameraExecutor = Executors.newSingleThreadExecutor();

        startCamera(); // Mulai kamera pertama kali

        takePhoto.setOnClickListener(v -> getTakePhoto());
        sensorFace.setOnClickListener(v -> getSensorFace());
        switchCamera.setOnClickListener(v -> getSwitchCamera());
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                cameraProvider.unbindAll();

                // Pilih kamera depan/belakang
                cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(isFrontCamera ?
                                CameraSelector.LENS_FACING_FRONT :
                                CameraSelector.LENS_FACING_BACK)
                        .build();

                // Buat Preview
                androidx.camera.core.Preview preview = new androidx.camera.core.Preview.Builder()
                        .build();

                preview.setSurfaceProvider(cameraView.getSurfaceProvider());

                // Setup ImageCapture
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                // Bind ke lifecycle
                camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture);

            } catch (Exception e) {
                Log.e("CameraX", "Gagal mengaktifkan kamera", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void getSwitchCamera() {
        isFrontCamera = !isFrontCamera; // Ganti kamera
        startCamera(); // Restart kamera dengan kamera baru
    }

    private void getTakePhoto() {
        if (imageCapture == null) return;

        File file = new File(getExternalFilesDir(null), System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder(file).build();

        imageCapture.takePicture(options, cameraExecutor, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(CameraActivtiy.this, ReviewCameraActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("image", file.getAbsolutePath());
                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                runOnUiThread(() -> Toast.makeText(CameraActivtiy.this, "Gagal mengambil foto", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void getSensorFace() {
        Toast.makeText(this, "Fitur deteksi wajah belum diimplementasikan", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
