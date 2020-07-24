package com.target.onlinecourierservice.ui.Shop;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.target.onlinecourierservice.Adapters.ProductAdapter;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.model.ProductAllData;
import com.target.onlinecourierservice.model.ProductModel;
import com.target.onlinecourierservice.ui.ParcelList.ParcelListViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class ShopFragment extends Fragment {

    private ShopViewModel mViewModel;
    RecyclerView recyclerView;
    ProductAdapter gridAdapter;
    EditText searchBar;
    ArrayList<ProductModel>productModels=new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(ShopViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_shop, container, false);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        searchBar=root.findViewById(R.id.searchBar);
        recyclerView=root.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        //gridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        final FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=database.getReference();
        databaseReference.child("Merchant-Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productModels.clear();
                for(final DataSnapshot keyNode : snapshot.getChildren()){
                    final ProductAllData productAllData=keyNode.getValue(ProductAllData.class);
                    if(productAllData!=null){
                        firebaseFirestore.collection("Merchants").document(productAllData.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(value!=null){
                                        productModels.add(new ProductModel(keyNode.getKey(),productAllData.getProduct_name(),value.getString("businessName"),productAllData.getId(),productAllData.getProduct_price(),productAllData.getImage_url()));
                                        gridAdapter.notifyDataSetChanged();
                                    }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search=searchBar.getText().toString();
                if(search.length()==0){
                    Query query=databaseReference.child("Merchant-Products").orderByChild("product_name").startAt(search).endAt(search+"\uf8ff");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            productModels.clear();
                            for(final DataSnapshot keyNode : snapshot.getChildren()){
                                final ProductAllData productAllData=keyNode.getValue(ProductAllData.class);
                                if(productAllData!=null){
                                    firebaseFirestore.collection("Merchants").document(productAllData.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if(value!=null){
                                                productModels.add(new ProductModel(keyNode.getKey(),productAllData.getProduct_name(),value.getString("businessName"),productAllData.getId(),productAllData.getProduct_price(),productAllData.getImage_url()));
                                                gridAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Query query=databaseReference.child("Merchant-Products").orderByChild("product_name").startAt(search.substring(0, 1).toUpperCase() + search.substring(1).toLowerCase()).endAt(search.substring(0, 1).toUpperCase() + search.substring(1).toLowerCase()+"\uf8ff");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            productModels.clear();
                            for(final DataSnapshot keyNode : snapshot.getChildren()){
                                final ProductAllData productAllData=keyNode.getValue(ProductAllData.class);
                                if(productAllData!=null){
                                    firebaseFirestore.collection("Merchants").document(productAllData.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if(value!=null){
                                                productModels.add(new ProductModel(keyNode.getKey(),productAllData.getProduct_name(),value.getString("businessName"),productAllData.getId(),productAllData.getProduct_price(),productAllData.getImage_url()));
                                                gridAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        };
        searchBar.addTextChangedListener(textWatcher);

        gridAdapter=new ProductAdapter(productModels,getActivity());
        recyclerView.setAdapter(gridAdapter);
        recyclerView.setHasFixedSize(true);

        return root;
    }


}