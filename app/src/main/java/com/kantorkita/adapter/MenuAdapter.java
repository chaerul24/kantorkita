package com.kantorkita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kantorkita.R;
import com.kantorkita.adapter.model.ModelMenu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ModelMenu> array;
    private final OnItemClick onItemClick;

    public MenuAdapter(Context context, ArrayList<ModelMenu> array, OnItemClick onItemClick) {
        this.context = context;
        this.array = array;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ModelMenu item = array.get(position);
        viewHolder.icon.setImageResource(item.icon);
        viewHolder.title.setText(item.title);
        viewHolder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (array != null) ? array.size() : 0;
    }

    public interface OnItemClick {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon_menu);
            title = itemView.findViewById(R.id.title_menu);
        }
    }
}
