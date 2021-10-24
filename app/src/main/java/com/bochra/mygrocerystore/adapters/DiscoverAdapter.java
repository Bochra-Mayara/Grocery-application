package com.bochra.mygrocerystore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bochra.mygrocerystore.R;
import com.bochra.mygrocerystore.activities.ViewAllActivity;
import com.bochra.mygrocerystore.models.DiscoverCategory;
import com.bochra.mygrocerystore.models.PopularModel;
import com.bumptech.glide.Glide;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class DiscoverAdapter extends Adapter<DiscoverAdapter.ViewHolder> {
    private Context context;
    private List<DiscoverCategory> categoryList;

    public DiscoverAdapter(Context context, List<DiscoverCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_cat_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getImg_url()).into(holder.img_url);
        holder.name.setText(categoryList.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",categoryList.get(position).getType());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


 

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_url;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_url = itemView.findViewById(R.id.imvcircular);
            name = itemView.findViewById(R.id.dis_pro);
        }
    }
}
