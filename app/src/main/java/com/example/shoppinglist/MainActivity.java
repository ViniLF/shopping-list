package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private Button btnNotify;

    private ArrayList<Item> itemList;
    private ItemAdapter adapter;
    private NotificationHelper notificationHelper;

    private ActivityResultLauncher<Intent> addItemLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String itemName = result.getData().getStringExtra("item_name");
                    if (itemName != null && !itemName.trim().isEmpty()) {
                        addItem(itemName);
                        Toast.makeText(this, "Item adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupClickListeners();

        notificationHelper = new NotificationHelper(this);
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        btnNotify = findViewById(R.id.btnNotify);

        itemList = new ArrayList<>();
        adapter = new ItemAdapter(itemList);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            removeItem(position);
        });
    }

    private void setupClickListeners() {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            addItemLauncher.launch(intent);
        });

        btnNotify.setOnClickListener(v -> {
            if (!itemList.isEmpty()) {
                notificationHelper.showShoppingNotification(itemList.size());
                Toast.makeText(this, "Lembrete enviado!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Adicione itens à lista primeiro!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addItem(String itemName) {
        if (itemName != null && !itemName.trim().isEmpty()) {
            Item newItem = new Item(itemName.trim());
            itemList.add(newItem);
            adapter.notifyItemInserted(itemList.size() - 1);
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            String itemName = itemList.get(position).getName();
            itemList.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, itemList.size());

            Toast.makeText(this, "\"" + itemName + "\" removido!", Toast.LENGTH_SHORT).show();
        }
    }
}