package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Settings extends Fragment {

    private CalendarView calendarView;
    private TextView noOutfitText;
    private TextView noOutfitPlanText;
    private Button addOutfitButton;
    private ViewPager2 outfitViewPager;
    private HashMap<String, List<Uri>> outfitPlans; // Store outfits by date

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize UI elements
        calendarView = view.findViewById(R.id.calendarView);
        noOutfitText = view.findViewById(R.id.noOutfitText);
        noOutfitPlanText = view.findViewById(R.id.noOutfitPlanText);
        addOutfitButton = view.findViewById(R.id.addOutfitButton);
        outfitViewPager = view.findViewById(R.id.outfitViewPager);


        // Event listeners
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String date = year + "-" + (month + 1) + "-" + dayOfMonth;
            if (outfitPlans.containsKey(date)) {
                showOutfits(outfitPlans.get(date));
            } else {
                showNoOutfitView();
            }
        });

        addOutfitButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SelectFitsActivity.class);
            startActivityForResult(intent, 1);

        });

        // Load any saved outfit plans
        outfitPlans = new HashMap<>();
        loadOutfitPlans();

        return view;
    }

    private void showNoOutfitView() {
        noOutfitText.setVisibility(View.VISIBLE);
        noOutfitPlanText.setVisibility(View.VISIBLE);
        addOutfitButton.setVisibility(View.VISIBLE);
        outfitViewPager.setVisibility(View.GONE);
    }

    private void showOutfits(List<Uri> outfits) {
        noOutfitText.setVisibility(View.GONE);
        noOutfitPlanText.setVisibility(View.GONE);
        addOutfitButton.setVisibility(View.GONE);
        outfitViewPager.setVisibility(View.VISIBLE);

        // Set up ViewPager with outfits
        OutfitPagerAdapter adapter = new OutfitPagerAdapter(outfits);
        outfitViewPager.setAdapter(adapter);
    }

    private void loadOutfitPlans() {
        // Load from persistent storage if necessary
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == requireActivity().RESULT_OK) {
            List<Uri> selectedItems = data.getParcelableArrayListExtra("selectedItems");
            Calendar calendar = Calendar.getInstance();
            long date = calendarView.getDate();
            calendar.setTimeInMillis(date);
            String dateString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

            outfitPlans.put(dateString, selectedItems);
            showOutfits(selectedItems);
        }
    }
}