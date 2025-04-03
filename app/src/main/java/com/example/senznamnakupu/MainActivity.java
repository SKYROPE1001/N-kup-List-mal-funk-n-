package com.example.senznamnakupu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemClickListener {

    private EditText editTextItem; // Pro zadání nové položky
    private Button buttonAdd; // Tlačítko pro přidání položky
    private RecyclerView recyclerView; // RecyclerView pro seznam položek
    private ArrayList<String> shoppingList = new ArrayList<>(); // Seznam položek
    private ItemAdapter itemAdapter; // Adapter pro RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializace UI komponent
        editTextItem = findViewById(R.id.editTextItem);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerView);

        // Nastavení RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(shoppingList, this); // Předání listeneru pro long click
        recyclerView.setAdapter(itemAdapter);

        // Tlačítko pro přidání položky
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editTextItem.getText().toString();
                if (!item.isEmpty()) {
                    shoppingList.add(item); // Přidání položky do seznamu
                    itemAdapter.notifyDataSetChanged(); // Aktualizace RecyclerView
                    editTextItem.setText(""); // Vymazání textu v EditText
                } else {
                    Toast.makeText(MainActivity.this, "Zadejte položku!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemLongClick(int position) {
        // Zobrazíme dialog pro úpravu nebo smazání položky
        final String item = shoppingList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Možnosti");
        builder.setItems(new String[]{"Upravit", "Smazat"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Funkce pro úpravu položky
                    showEditDialog(position);
                } else {
                    // Funkce pro smazání položky
                    shoppingList.remove(position);
                    itemAdapter.notifyDataSetChanged(); // Aktualizace RecyclerView
                    Toast.makeText(MainActivity.this, "Položka smazána", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    private void showEditDialog(final int position) {
        // Dialog pro úpravu položky
        final EditText editText = new EditText(MainActivity.this);
        editText.setText(shoppingList.get(position));

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Upravit položku");
        builder.setView(editText);

        builder.setPositiveButton("Uložit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedItem = editText.getText().toString();
                if (!updatedItem.isEmpty()) {
                    shoppingList.set(position, updatedItem); // Aktualizace položky
                    itemAdapter.notifyDataSetChanged(); // Aktualizace RecyclerView
                    Toast.makeText(MainActivity.this, "Položka upravena", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Zrušit", null);
        builder.show();
    }
}
