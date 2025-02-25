package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class SelectFits extends AppCompatActivity implements FragmentItems.OnItemSelectedListener {

    private Button addButton;
    private ArrayList<Uri> selectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectfits);

        FragmentItems fragmentItems = new FragmentItems();
        fragmentItems.setMode(FragmentItems.MODE_SELECT_FITS);
        fragmentItems.setOnItemSelectedListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragmentItems);
        transaction.commit();

        addButton = findViewById(R.id.button2);
        addButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("selectedItems", selectedItems);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    public void onItemSelected(List<Uri> items) {
        selectedItems.clear();
        selectedItems.addAll(items);
    }
}