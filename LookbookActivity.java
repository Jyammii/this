package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.net.Uri; // Ensure this import is present
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class LookbookActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private LookbookAdapter adapter;
    private Button createLookbookButton;
    private List<Uri> selectedItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookbook);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        createLookbookButton = findViewById(R.id.create_lookbook_button);

        selectedItems = new ArrayList<>();

        setupTabs();

        createLookbookButton.setOnClickListener(v -> {
            if (selectedItems != null && !selectedItems.isEmpty()) {
                Intent intent = new Intent(LookbookActivity.this, LookbookDetailActivity.class);
                intent.putParcelableArrayListExtra("selectedItems", new ArrayList<>(selectedItems));
                intent.putExtra("lookbookName", getIntent().getStringExtra("lookbookName"));
                startActivity(intent);
            } else {
                Toast.makeText(LookbookActivity.this, "No items selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupTabs() {
        List<Fragment> fragments = new ArrayList<>();
        FragmentItems fragmentItems = new FragmentItems();
        fragmentItems.setMode(FragmentItems.MODE_SELECT_FITS);
        fragmentItems.setOnItemSelectedListener(items -> selectedItems = items);

        fragments.add(fragmentItems);
        fragments.add(new fragment_outfits3());

        adapter = new LookbookAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Items");
                    break;
                case 1:
                    tab.setText("Outfits");
                    break;
            }
        }).attach();
    }
}