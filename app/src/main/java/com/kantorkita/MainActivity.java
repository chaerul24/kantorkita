package com.kantorkita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn_submit;
    private EditText input_email, input_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_email = findViewById(R.id.input_email);
        input_pass = findViewById(R.id.input_pass);
        btn_submit = findViewById(R.id.btn_submit);
        TextView text_reset_pass = findViewById(R.id.text_reset_pass);

        btn_submit.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}