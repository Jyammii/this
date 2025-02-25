package com.example.closetifiy_finalproject;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;

public class HomeCloset extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private HomeClosetAdapter adapter;
    private Uri combinedImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_closet, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        if (getActivity() != null && getActivity().getIntent() != null) {
            String uriString = getActivity().getIntent().getStringExtra("combinedImageUri");
            if (uriString != null) {
                combinedImageUri = Uri.parse(uriString);
                Log.d("HomeCloset", "Received combined image URI: " + combinedImageUri.toString());
            }
        }

        setupTabs();

        return view;
    }

    private void setupTabs() {
        List<Fragment> fragments = new ArrayList<>();
        FragmentItems fragmentItems = new FragmentItems();
        fragmentItems.setMode(FragmentItems.MODE_HOME_CLOSET);
        fragments.add(fragmentItems);

        fragment_outfits3 outfitsFragment = new fragment_outfits3();
        if (combinedImageUri != null) {
            outfitsFragment.setCombinedImageUri(combinedImageUri);
        }
        fragments.add(outfitsFragment);

        fragments.add(new fragment_lookbbok());

        adapter = new HomeClosetAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Items");
                    break;
                case 1:
                    tab.setText("Outfits");
                    break;
                case 2:
                    tab.setText("Lookbooks");
                    break;
            }
        }).attach();
    }
}