package com.bochra.mygrocerystore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bochra.mygrocerystore.R;
import com.bochra.mygrocerystore.models.PopularModel;
import com.bochra.mygrocerystore.models.RecommendedModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecommededAdapter extends RecyclerView.Adapter<RecommededAdapter.ViewHolder> {
    private Context context;
    private List<RecommendedModel> recommendedModelList;

    public RecommededAdapter(Context context, List<RecommendedModel> recommendedModelList) {
        this.context = context;
        this.recommendedModelList = recommendedModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(recommendedModelList.get(position).getImg_url()).into(holder.img_url);
        holder.name.setText(recommendedModelList.get(position).getName());
        holder.rating.setText(recommendedModelList.get(position).getRating());
        holder.description.setText(recommendedModelList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return recommendedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_url;
        TextView name,description,rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_url = itemView.findViewById(R.id.rec_img);
            name = itemView.findViewById(R.id.rec_name);
            description = itemView.findViewById(R.id.rec_des);
            rating= itemView.findViewById(R.id.rec_rating);

        }
    }
}
