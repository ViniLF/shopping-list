package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Item> items;
    private OnItemClickListener listener;

    // Interface para clicks nos itens
    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    // Construtor
    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    // Setter para o listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.tvItemName.setText(item.getName());

        // Click listener para o botão delete
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder interno
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        ImageButton btnDelete;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}