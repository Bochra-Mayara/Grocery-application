package com.bochra.mygrocerystore.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bochra.mygrocerystore.R;
import com.bochra.mygrocerystore.models.ViewAllModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    TextView quantity;
    int totalQuantity = 1;
    int totalPrice = 0;
    ImageView detailedImg;
    TextView rating,description,price;
    Button addtocart;
    ImageView addItem,removeItem;
    Toolbar toolbar;
    ViewAllModel viewAllModel = null;
    FirebaseFirestore firestore;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Object object = getIntent().getSerializableExtra("form");
        if(object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;

        }
        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.add_remove);
        addtocart = findViewById(R.id.add_cart);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_des);
        price = findViewById(R.id.detailed_price);
        if(viewAllModel != null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
           rating .setText(viewAllModel.getRating());
           price .setText("Price :"+viewAllModel.getPrice()+"/kg");
          description.setText(viewAllModel.getDescription());
          totalPrice = viewAllModel.getPrice()*totalQuantity;
            if(viewAllModel.getType().equals("milk")){
                price.setText("Price :"+viewAllModel.getPrice()+"/litre");
                totalPrice = viewAllModel.getPrice()*totalQuantity;
            }
            if(viewAllModel.getType().equals("eggs")){
                price.setText("Price :"+viewAllModel.getPrice()+"/dozen");
                totalPrice = viewAllModel.getPrice()*totalQuantity;
            }
        }
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice()*totalQuantity;
                }

            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice()*totalQuantity;
                }


            }
        });
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedToCart();
            }


        });


    }

    private void addedToCart() {
        String saveCurrantTime,saveCurranrDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currantDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurranrDate = currantDate.format(calForDate.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrantTime = currentTime.format(calForDate.getTime());


        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currantDate",saveCurranrDate);
        cartMap.put("currantTime",saveCurrantTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this,"Added To A Cart",Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }


}