package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.net.Uri; // Ensure this import is present
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LookbookDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView lookbookNameTextView;
    private ArrayList<Uri> selectedItems;
    private String lookbookName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_lookbook_detail);

        recyclerView = findViewById(R.id.recyclerView);
        lookbookNameTextView = findViewById(R.id.lookbook_name);

        selectedItems = getIntent().getParcelableArrayListExtra("selectedItems");
        lookbookName = getIntent().getStringExtra("lookbookName");

        lookbookNameTextView.setText(lookbookName);

        // Set up RecyclerView adapter and item click listener
        LookbookItemsAdapter adapter = new LookbookItemsAdapter(this, selectedItems);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((position) -> {
            // Open item detail page
            Intent intent = new Intent(LookbookDetailActivity.this, ItemDetailed.class);
            intent.putExtra("imageUri", selectedItems.get(position).toString());
            startActivity(intent);
        });
    }
}