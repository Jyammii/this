package com.example.closetifiy_finalproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private Context context;
    private List<Uri> itemsList;
    private OnItemSelectedListener onItemSelectedListener;
    private OnItemClickListener onItemClickListener;
    private int mode;
    private Set<Integer> selectedPositions = new HashSet<>();

    public interface OnItemSelectedListener {
        void onItemSelected(List<Uri> selectedItems);
    }

    public interface OnItemClickListener {
        void onItemClick(Uri item, int position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ItemsAdapter(Context context, List<Uri> itemsList, int mode) {
        this.context = context;
        this.itemsList = itemsList;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri item = itemsList.get(position);
        holder.imageView.setImageURI(item);

        if (selectedPositions.contains(position)) {
            holder.imageView.setColorFilter(Color.parseColor("#9EB7ED"), PorterDuff.Mode.MULTIPLY);
        } else {
            holder.imageView.clearColorFilter();
        }

        holder.itemView.setOnClickListener(v -> {
            if (mode == FragmentItems.MODE_HOME_CLOSET) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item, position);
                }
            } else if (mode == FragmentItems.MODE_SELECT_FITS) {
                if (selectedPositions.contains(position)) {
                    selectedPositions.remove(position);
                    holder.imageView.clearColorFilter();
                } else {
                    selectedPositions.add(position);
                    holder.imageView.setColorFilter(Color.parseColor("#9EB7ED"), PorterDuff.Mode.MULTIPLY);
                }
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(getSelectedItems());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private List<Uri> getSelectedItems() {
        List<Uri> selectedItems = new ArrayList<>();
        for (Integer position : selectedPositions) {
            selectedItems.add(itemsList.get(position));
        }
        return selectedItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}