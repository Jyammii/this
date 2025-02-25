package com.example.closetifiy_finalproject;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class fragment_outfits3 extends Fragment {

    private RecyclerView recyclerView;
    private OutfitAdapter adapter;
    private ArrayList<Uri> imageUris = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outfits3, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new OutfitAdapter(imageUris);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setCombinedImageUri(Uri imageUri) {
        this.imageUris.clear();
        this.imageUris.add(imageUri);
        adapter.notifyDataSetChanged();
    }
}