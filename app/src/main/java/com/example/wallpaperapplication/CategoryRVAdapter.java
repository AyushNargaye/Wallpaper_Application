package com.example.wallpaperapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private ArrayList<RLCategoryModel> rlCategoryModelArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<RLCategoryModel> rlCategoryModelArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.rlCategoryModelArrayList = rlCategoryModelArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }
    //    public CategoryRVAdapter(ArrayList<RLCategoryModel> rlCategoryModelArrayList, Context context) {
//        this.rlCategoryModelArrayList = rlCategoryModelArrayList;
//        this.context = context;
//    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_rv_item, parent, false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RLCategoryModel  rlCategoryModel= rlCategoryModelArrayList.get(position);
        holder.categoryTV.setText(rlCategoryModel.getCategory());
        Glide.with(context).load(rlCategoryModel.getCategoryIVUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rlCategoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTV;
        private ImageView categoryIV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
            categoryTV = itemView.findViewById(R.id.idTVCategory);
        }
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }
}
