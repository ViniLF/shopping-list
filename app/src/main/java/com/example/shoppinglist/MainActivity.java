package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private Button btnNotify;

    // Variáveis da lista (implementação do Kaua)
    private ArrayList<Item> itemList;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupClickListeners();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        btnNotify = findViewById(R.id.btnNotify);

        // Inicializar a lista e adapter (parte do Kaua)
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(itemList);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Configurar listener para deletar itens (parte do Kaua)
        adapter.setOnItemClickListener(position -> {
            removeItem(position);
        });
    }

    private void setupClickListeners() {
        fabAdd.setOnClickListener(v -> {
            // TODO: Adrian vai implementar o Intent aqui
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        btnNotify.setOnClickListener(v -> {
            // TODO: Adrian vai implementar a notificação aqui
            if (!itemList.isEmpty()) {
                Toast.makeText(this, "Você tem " + itemList.size() + " itens para comprar!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Adicione itens à lista primeiro!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Métodos para gerenciar a lista (implementação do Kaua)
    public void addItem(String itemName) {
        if (itemName != null && !itemName.trim().isEmpty()) {
            Item newItem = new Item(itemName.trim());
            itemList.add(newItem);
            adapter.notifyItemInserted(itemList.size() - 1);

            Toast.makeText(this, "Item adicionado com sucesso!", Toast.LENGTH_SHORT).show();
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

    // Método para testar - adiciona alguns itens exemplo
    private void addSampleItems() {
        addItem("Leite");
        addItem("Pão");
        addItem("Ovos");
    }
}