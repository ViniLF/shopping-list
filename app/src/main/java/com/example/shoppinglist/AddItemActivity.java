package com.example.shoppinglist;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText etItemName;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        etItemName = findViewById(R.id.etItemName);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupClickListeners() {
        btnSave.setOnClickListener(v -> {
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });
    }
}