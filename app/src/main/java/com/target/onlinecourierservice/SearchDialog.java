package com.target.onlinecourierservice;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.target.onlinecourierservice.Adapters.ProductAdapter;
import com.target.onlinecourierservice.model.Merchant;
import com.target.onlinecourierservice.model.ProductAllData;
import com.target.onlinecourierservice.model.ProductModel;

import java.util.ArrayList;


public class SearchDialog extends DialogFragment {

    public static final String TAG = "Search";
    ImageButton back;
    RecyclerView recyclerView;
    ProductAdapter gridAdapter;
    EditText searchBar;
    ArrayList<ProductModel> productModels=new ArrayList<>();


    public static SearchDialog display(FragmentManager fragmentManager){
       SearchDialog searchDialog=new SearchDialog();
       searchDialog.show(fragmentManager,TAG);
        return searchDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.search_dialog, container, false);

        back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        searchBar=view.findViewById(R.id.searchBar);
        recyclerView=view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        //gridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=database.getReference();

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
                    });
                }
            }
        };
        searchBar.addTextChangedListener(textWatcher);

        gridAdapter=new ProductAdapter(productModels,getActivity());
        recyclerView.setAdapter(gridAdapter);
        recyclerView.setHasFixedSize(true);




        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }
}
