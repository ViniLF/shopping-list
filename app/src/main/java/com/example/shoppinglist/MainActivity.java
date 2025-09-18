package com.example.shoppinglist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
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


    private static final String CHANNEL_ID = "shopping_list_channel";
    private static final int NOTIFICATION_ID = 1;


    private ActivityResultLauncher<Intent> addItemLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupClickListeners();


        createNotificationChannel();


        setupActivityResultLauncher();
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


    private void setupActivityResultLauncher() {
        addItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                // Receber dados do item da AddItemActivity
                                String itemName = data.getStringExtra("item_name");
                                if (itemName != null && !itemName.trim().isEmpty()) {
                                    addItem(itemName);
                                }
                            }
                        }
                    }
                }
        );
    }

    private void setupClickListeners() {
        fabAdd.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            addItemLauncher.launch(intent);
        });

        btnNotify.setOnClickListener(v -> {

            showNotification();
        });
    }


    public void addItem(String itemName) {
        if (itemName != null && !itemName.trim().isEmpty()) {
            Item newItem = new Item(itemName.trim());
            itemList.add(newItem);
            adapter.notifyItemInserted(itemList.size() - 1);


            Toast.makeText(this, "Item \"" + itemName + "\" adicionado à lista!", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            String itemName = itemList.get(position).getName();
            itemList.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, itemList.size());


            Toast.makeText(this, "\"" + itemName + "\" removido da lista!", Toast.LENGTH_SHORT).show();
        }
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Lista de Compras";
            String description = "Notificações da lista de compras";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void showNotification() {
        int itemCount = itemList.size();
        String title = "Lista de Compras";
        String message;

        if (itemCount == 0) {
            message = "Sua lista está vazia! Adicione alguns itens.";
        } else if (itemCount == 1) {
            message = "Você tem 1 item na sua lista de compras.";
        } else {
            message = "Você tem " + itemCount + " itens na sua lista de compras.";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Ícone padrão do sistema
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());


        Toast.makeText(this, "Notificação enviada! ✓", Toast.LENGTH_SHORT).show();
    }


    private void addSampleItems() {
        addItem("Leite");
        addItem("Pão");
        addItem("Ovos");
    }
}