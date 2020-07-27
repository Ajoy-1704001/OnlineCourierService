package com.target.onlinecourierservice.ui.MyOrder;

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
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.target.onlinecourierservice.Adapters.OrderAdapter;
import com.target.onlinecourierservice.Adapters.ParcelAdapter;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.model.CartDisplayLIst;
import com.target.onlinecourierservice.model.CartOrder;
import com.target.onlinecourierservice.model.ParcelDisplay;
import com.target.onlinecourierservice.model.ParcelModel;
import com.target.onlinecourierservice.ui.home.HomeViewModel;

import java.util.ArrayList;

public class MyOrderFragment extends Fragment {

    private MyOrderViewModel mViewModel;
    RecyclerView recyclerView;
    OrderAdapter listAdapter;
    ArrayList<CartDisplayLIst> parcelDisplayArrayList=new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(MyOrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_order, container, false);

        recyclerView=root.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        final String userID=currentUser.getUid();

        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference();


        reference.child("Purchase-Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parcelDisplayArrayList.clear();
                for (final DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    final CartOrder cartOrder=keyNode.getValue(CartOrder.class);
                    if(cartOrder!=null){
                        if(cartOrder.getUserID().equals(userID)){
                            reference.child("Order-Status").child(cartOrder.getOrderNo()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String status=snapshot.getValue(String.class);
                                    parcelDisplayArrayList.add(new CartDisplayLIst(cartOrder.getOrderNo(),cartOrder.getDate(),status));
                                    listAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
                listAdapter = new OrderAdapter(parcelDisplayArrayList,getContext());
                recyclerView.setAdapter(listAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }

}