package com.target.onlinecourierservice.ui.ParcelList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.target.onlinecourierservice.Adapters.ParcelAdapter;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.model.ParcelDisplay;
import com.target.onlinecourierservice.model.ParcelModel;
import com.target.onlinecourierservice.ui.CreateOrder.CreateOrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParcelListFragment extends Fragment {

    private ParcelListViewModel mViewModel;
    RecyclerView recyclerView;
    ParcelAdapter listAdapter;
    ArrayList<ParcelDisplay>parcelDisplayArrayList=new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(ParcelListViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_parcel_list, container, false);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        final String userID="userid12345";//Here userid will be auth uid

        recyclerView=root.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference();
        reference.child("Parcel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parcelDisplayArrayList.clear();
                for (final DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    final ParcelModel parcelModel=keyNode.getValue(ParcelModel.class);
                    if(parcelModel!=null){
                        if(parcelModel.getUserId().equals(userID)){
                            reference.child("Parcel_Status").child(keyNode.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String status=snapshot.getValue(String.class);
                                    parcelDisplayArrayList.add(new ParcelDisplay(keyNode.getKey(),parcelModel.getDate(),status));
                                    listAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                }
                listAdapter = new ParcelAdapter(parcelDisplayArrayList,getContext());
                recyclerView.setAdapter(listAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return root;
    }


}