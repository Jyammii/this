package com.example.closetifiy_finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {

    private List<String> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public BottomSheetAdapter(List<String> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public BottomSheetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BottomSheetAdapter.ViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOption;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOption = itemView.findViewById(R.id.tvOption);
        }

        public void bind(final String item, final OnItemClickListener listener) {
            tvOption.setText(item);
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}