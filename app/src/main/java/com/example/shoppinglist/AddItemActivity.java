package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
            addItem();
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });
    }


    private void addItem() {
        String itemName = etItemName.getText().toString().trim();


        if (itemName.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o nome do item", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent resultIntent = new Intent();
        resultIntent.putExtra("item_name", itemName);


        setResult(RESULT_OK, resultIntent);


        Toast.makeText(this, "Item '" + itemName + "' adicionado!", Toast.LENGTH_SHORT).show();


        finish();
    }
}