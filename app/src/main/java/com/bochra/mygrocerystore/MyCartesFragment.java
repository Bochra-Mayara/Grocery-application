package com.bochra.mygrocerystore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bochra.mygrocerystore.activities.PlacedOrderActivity;
import com.bochra.mygrocerystore.adapters.MyCarteAdapter;
import com.bochra.mygrocerystore.adapters.ViewAllAdapter;
import com.bochra.mygrocerystore.models.MyCarteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCartesFragment extends Fragment {
    RecyclerView recyclerView;
    MyCarteAdapter myCarteAdapter;
    List<MyCarteModel>carteModelList;
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount;
    ProgressBar progressBar;
    Button buyNow;



    public MyCartesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_cartes, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        progressBar = root.findViewById(R.id.progressbar);
        buyNow = root.findViewById(R.id.buy_now);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);
        overTotalAmount = root.findViewById(R.id.textView7);







        carteModelList = new ArrayList<>();
        myCarteAdapter = new MyCarteAdapter(getActivity(), carteModelList);
        recyclerView.setAdapter(myCarteAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        String documentId = documentSnapshot.getId();
                        MyCarteModel carteModel = documentSnapshot.toObject(MyCarteModel.class);
                        carteModel.setDocumentId(documentId);
                        carteModelList.add(carteModel);
                        myCarteAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    calculateTotalAmount(carteModelList);
                }
            }
        });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                intent.putExtra("itemList", (Serializable) carteModelList);
                startActivity(intent);

            }
        });


        return  root;
    }

    private void calculateTotalAmount(List<MyCarteModel> carteModelList) {
        double totalAmount = 0.0;
        for(MyCarteModel myCarteModel : carteModelList){
            totalAmount += myCarteModel.getTotalPrice();
        }
        overTotalAmount.setText("Total Amount :"+totalAmount);
    }

}