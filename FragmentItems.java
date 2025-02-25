package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FragmentItems extends Fragment {

    public static final int MODE_HOME_CLOSET = 1;
    public static final int MODE_SELECT_FITS = 2;

    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private List<Uri> itemsList;
    private OnItemSelectedListener onItemSelectedListener;
    private int mode;

    public interface OnItemSelectedListener {
        void onItemSelected(List<Uri> items);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        itemsList = NewItem.getImageUris(); // Get the image URIs from NewItem
        itemsAdapter = new ItemsAdapter(getContext(), itemsList, mode);
        itemsAdapter.setOnItemSelectedListener(selectedItems -> {
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(selectedItems);
            }
        });
        itemsAdapter.setOnItemClickListener((item, position) -> {
            if (mode == MODE_HOME_CLOSET) {
                Intent intent = new Intent(getContext(), ItemDetailed.class);
                intent.putExtra("imageUri", item.toString());
                intent.putExtra("position", position);
                startActivity(intent);
            } else if (mode == MODE_SELECT_FITS) {
                // Handle item selection for SelectFits
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(itemsList);
                }
            }
        });
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }

    public void addItem(Uri imagePath) {
        itemsList.add(imagePath);
        itemsAdapter.notifyItemInserted(itemsList.size() - 1);
    }
}