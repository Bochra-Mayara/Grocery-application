package com.bochra.mygrocerystore.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bochra.mygrocerystore.R;
import com.bochra.mygrocerystore.activities.DetailedActivity;
import com.bochra.mygrocerystore.activities.ViewAllActivity;
import com.bochra.mygrocerystore.models.ViewAllModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    Context context;
    List<ViewAllModel>viewAllModelList;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelList) {
        this.context = context;
        this.viewAllModelList = viewAllModelList;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(viewAllModelList.get(position).getImg_url()).into(holder.img_url);
        holder.name.setText(viewAllModelList.get(position).getName());
        holder.rating.setText(viewAllModelList.get(position).getRating());
        holder.description.setText(viewAllModelList.get(position).getDescription());
        holder.price.setText(viewAllModelList.get(position).getPrice()+"/kg");
        if(viewAllModelList.get(position).getType().equals("milk")){
            holder.price.setText(viewAllModelList.get(position).getPrice()+"/litre");
        }
        if(viewAllModelList.get(position).getType().equals("eggs")){
            holder.price.setText(viewAllModelList.get(position).getPrice()+"/dozen");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("form",viewAllModelList.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return viewAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_url;
        TextView name,description,rating,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_url = itemView.findViewById(R.id.view_img);
            name = itemView.findViewById(R.id.view_name);
            description = itemView.findViewById(R.id.view_description);
            price = itemView.findViewById(R.id.view_price);
            rating= itemView.findViewById(R.id.view_rating);
        }
    }
}
