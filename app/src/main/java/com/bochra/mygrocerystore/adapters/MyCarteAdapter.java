package com.bochra.mygrocerystore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bochra.mygrocerystore.R;
import com.bochra.mygrocerystore.models.MyCarteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCarteAdapter extends RecyclerView.Adapter<MyCarteAdapter.ViewHolder> {
    FirebaseFirestore db;
    FirebaseAuth auth;
    Context context;
    List<MyCarteModel>carteModelList;
    int totalPrice = 0;

    public MyCarteAdapter(Context context, List<MyCarteModel> carteModelList) {
        this.context = context;
        this.carteModelList = carteModelList;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(carteModelList.get(position).getProductName());
        holder.price.setText(carteModelList.get(position).getProductPrice());
        holder.date.setText(carteModelList.get(position).getCurrantDate());
        holder.time.setText(carteModelList.get(position).getCurrantTime());
        holder.totalPrice.setText(String.valueOf(carteModelList.get(position).getTotalPrice()));
        holder.quantity.setText(carteModelList.get(position).getTotalQuantity());



        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart")
                        .document(carteModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    carteModelList.remove(carteModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "item Deleted",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


            }
        });



       }

    @Override
    public int getItemCount() {
        return carteModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView name,price,date,time,quantity,totalPrice;
       ImageView deleteItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.currant_date);
            time = itemView.findViewById(R.id.currant_time);
            quantity = itemView.findViewById(R.id.total_price);
            totalPrice = itemView.findViewById(R.id.total_quantity);
            deleteItem = itemView.findViewById(R.id.delete);
        }
    }
}
