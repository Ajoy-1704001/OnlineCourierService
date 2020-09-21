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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

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
import com.target.onlinecourierservice.Global;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.SearchDialog;
import com.target.onlinecourierservice.model.Merchant;
import com.target.onlinecourierservice.model.ProductAllData;
import com.target.onlinecourierservice.model.ProductModel;
import com.target.onlinecourierservice.ui.ParcelList.ParcelListViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class ShopFragment extends Fragment {

    private ShopViewModel mViewModel;
    ImageButton search;
    Button category;
    RecyclerView recyclerView;
    ProductAdapter gridAdapter;
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
        search=root.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });

        category=root.findViewById(R.id.category);
        category.setText(Global.category);


        recyclerView=root.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        //gridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(gridLayoutManager);


        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=database.getReference();

        CategoryProduct(Global.category);
        /*databaseReference.child("Merchant-Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productModels.clear();
                for(final DataSnapshot keyNode : snapshot.getChildren()){
                    final ProductAllData productAllData=keyNode.getValue(ProductAllData.class);
                    if(productAllData!=null){
                        databaseReference.child("Merchants").child(productAllData.getId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Merchant merchant=snapshot.getValue(Merchant.class);
                                productModels.add(new ProductModel(keyNode.getKey(),productAllData.getProduct_name(),merchant.getBusinessName(),productAllData.getId(),productAllData.getProduct_price(),productAllData.getImage_url(),productAllData.getProduct_description(),productAllData.getProduct_discount_price(),productAllData.getProduct_discount_percentage()));
                                gridAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



        gridAdapter=new ProductAdapter(productModels,getActivity());
        recyclerView.setAdapter(gridAdapter);
        recyclerView.setHasFixedSize(true);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getActivity(),v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.category_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        category.setText(item.getTitle().toString());
                        productModels.clear();
                        gridAdapter.notifyDataSetChanged();
                        CategoryProduct(item.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();

            }
        });

        return root;
    }

    private void CategoryProduct(final String name) {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference();

        databaseReference.child("Merchant-Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productModels.clear();
                for(final DataSnapshot keyNode : snapshot.getChildren()){
                    final ProductAllData productAllData=keyNode.getValue(ProductAllData.class);
                    if(productAllData!=null){
                        if(productAllData.getProduct_cat().equals(name)||name.equals("All Category")){
                            databaseReference.child("Merchants").child(productAllData.getId()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Merchant merchant=snapshot.getValue(Merchant.class);
                                    productModels.add(new ProductModel(keyNode.getKey(),productAllData.getProduct_name(),merchant.getBusinessName(),productAllData.getId(),productAllData.getProduct_price(),productAllData.getImage_url(),productAllData.getProduct_description(),productAllData.getProduct_discount_price(),productAllData.getProduct_discount_percentage()));
                                    gridAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void openSearch() {
        SearchDialog.display(getChildFragmentManager());
    }
}