package com.example.wallpaperapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WallpaperRVAdapter extends RecyclerView.Adapter<WallpaperRVAdapter.ViewHolder> {
    private ArrayList<String> wallpaperRVArrayList;
    private Context context;

    public WallpaperRVAdapter(ArrayList<String> wallpaperRVArrayList, Context context) {
        this.wallpaperRVArrayList = wallpaperRVArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item, parent, false);
        return new WallpaperRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(wallpaperRVArrayList.get(position)).into(holder.wallpaperIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, WallpaperActivity.class);
                i.putExtra("imgUrl", wallpaperRVArrayList.get(position));
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return wallpaperRVArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView wallpaperIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperIV = itemView.findViewById(R.id.idIVWallpaper);
        }
    }
}
