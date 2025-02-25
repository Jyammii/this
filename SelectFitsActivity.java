package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class SelectFitsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private List<Uri> itemsList;
    private List<Uri> selectedOutfitUris = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_fits);

        recyclerView = findViewById(R.id.recyclerView);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        // Populate itemsList with URIs (This is a placeholder. Replace with actual data source)
        itemsList = NewItem.getImageUris();

        itemsAdapter = new ItemsAdapter(this, itemsList, FragmentItems.MODE_SELECT_FITS);
        itemsAdapter.setOnItemSelectedListener(selectedItems -> {
            selectedOutfitUris = selectedItems;
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(itemsAdapter);

        btnConfirm.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("selectedItems", new ArrayList<>(selectedOutfitUris));
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}