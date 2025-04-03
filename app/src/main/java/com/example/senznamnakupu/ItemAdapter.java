package com.example.senznamnakupu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<String> shoppingList; // Seznam položek
    private ItemClickListener itemClickListener; // Poslech pro kliknutí

    // Konstruktor
    public ItemAdapter(ArrayList<String> shoppingList, ItemClickListener itemClickListener) {
        this.shoppingList = shoppingList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflace layoutu pro každou položku
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        // Nastavení textu pro každou položku
        String item = shoppingList.get(position);
        holder.textView.setText(item);

        // Dlouhý klik na položku pro úpravu nebo smazání
        holder.itemView.setOnLongClickListener(v -> {
            itemClickListener.onItemLongClick(position); // Zavolání metody v hlavní aktivitě
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size(); // Počet položek v seznamu
    }

    // ViewHolder pro položky
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    // Interface pro dlouhý klik na položku
    public interface ItemClickListener {
        void onItemLongClick(int position);
    }
}
